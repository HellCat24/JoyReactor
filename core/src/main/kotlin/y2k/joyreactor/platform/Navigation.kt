package y2k.joyreactor.platform

import y2k.joyreactor.Message
import y2k.joyreactor.Post

/**
 * Created by y2k on 02/10/15.
 */
interface Navigation {

    fun switchProfileToLogin()

    fun switchLoginToProfile()

    fun closeCreateComment()

    fun closeAddTag()

    fun openPost(postId: String)

    fun openBrowser(url: String)

    fun openVideo(postId: String)

    fun openVideo(post: Post)

    fun openImageView(post: Post)

    fun openCreateComment()

    fun openPostGallery()

    fun openTags()

    fun openDialogs()

    fun openLogin()

    fun openMessages(dialog : Message)

    val argumentPostId: String

    companion object {

        val instance: Navigation
            get() = Platform.instance.navigator
    }
}