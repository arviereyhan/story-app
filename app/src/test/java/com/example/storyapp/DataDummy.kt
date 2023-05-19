package com.example.storyapp

import com.example.storyapp.data.remote.response.ListStoryItem
import com.example.storyapp.data.remote.response.StoryResponse

object DataDummy {
    fun generateDummyStoryResponse(): StoryResponse{
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                i.toString(),
                "name + $i",
                "description + $i",
                "id + $i",
                i.toDouble(),
                i.toDouble(),
            )
            items.add(story)
        }
        return StoryResponse(
            error = false,
            message = "Stories fetched successfully",
            listStory = items
        )
    }
}
