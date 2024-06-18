package org.vikkio.data.jdbcSqliteDb

import java.sql.Connection
import java.sql.SQLException
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties
import org.vikkio.data.jdbcSqliteDb.annotations.*

object DbHelper {
    fun migrateIfNotExists(connection: Connection, entityClass: KClass<out Any>): Boolean {
        val tableName = entityClass.simpleName?.lowercase() ?: return false
        val columns =
            entityClass.memberProperties.filter { it.findAnnotation<Ignore>() == null }.joinToString(", ") { property ->
                val columnName = property.name
                val columnType = when (property.returnType.classifier) {
                    String::class -> "VARCHAR(255)"
                    Int::class -> "INT"
                    Boolean::class -> "BOOLEAN"
                    else -> "VARCHAR(255)" // default to VARCHAR for unsupported types
                }

                // Check for @Id
                val idAnnotation = property.findAnnotation<Id>()
                val primaryKeyClause = if (idAnnotation != null) " PRIMARY KEY" else ""

                // Check for @Unique
                val uniqueAnnotation = property.findAnnotation<Unique>()
                val uniqueConstraint = if (uniqueAnnotation != null) " UNIQUE" else ""


                "$columnName $columnType$primaryKeyClause$uniqueConstraint"
            }

        val createTableSQL = """
            CREATE TABLE IF NOT EXISTS $tableName (
                $columns
            );
        """.trimIndent()

        return try {
            val statement = connection.createStatement()
            statement.execute(createTableSQL)
            true
        } catch (e: SQLException) {
            println("An error occurred while creating the table: ${e.message}")
            false
        }
    }
}