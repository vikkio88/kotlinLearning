package org.vikkio.exercises

import org.vikkio.interfaces.Exercise

class Lambdas : Exercise {
    override val title: String
        get() = "Functions - Lambdas - 1"

    // You have a list of actions supported by a web service, a common prefix for all requests,
    // and an ID of a particular resource.
    // To request an action title over the resource with ID: 5,
    // you need to create the following URL: https://example.com/book-info/5/title.
    // Use a lambda expression to create a list of URLs from the list of actions.
    override fun body() {
        val actions = listOf("title", "year", "author")
        val prefix = "https://example.com/book-info"
        val id = 5
        // return in lambdas is implicit
        val urls = actions.map { "$prefix/$id/$it" }
        println(urls)
    }
}