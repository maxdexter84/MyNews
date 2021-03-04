package ru.maxdexter.mynews.domain.mappers

import ru.maxdexter.mynews.data.entity.db.Bookmark
import ru.maxdexter.mynews.ui.entity.UIModel

class UIModelBookmark {
    fun fromBookmarkToUIModel(bookmark: Bookmark): UIModel {
        return UIModel(
            id = bookmark.id,
            title = bookmark.title,
            description = bookmark.description,
            publishedAt = bookmark.publishedAt,
            sourceName = bookmark.sourceName,
            url = bookmark.url,
            urlToImage = bookmark.urlToImage
        )
    }

    fun fromUIModelToUIBookmark(uiModel: UIModel): Bookmark {
        return Bookmark(
            id = uiModel.id,
            title = uiModel.title,
            description = uiModel.description,
            publishedAt = uiModel.publishedAt,
            sourceName = uiModel.sourceName,
            url = uiModel.url,
            urlToImage = uiModel.urlToImage
        )
    }
}