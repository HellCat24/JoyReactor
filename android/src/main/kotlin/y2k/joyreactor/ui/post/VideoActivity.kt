package y2k.joyreactor.ui.post

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.VideoView
import y2k.joyreactor.Post
import y2k.joyreactor.R
import y2k.joyreactor.common.ServiceLocator
import y2k.joyreactor.presenters.VideoPresenter
import y2k.joyreactor.ui.base.ToolBarActivity
import java.io.File

class VideoActivity : ToolBarActivity() {

    companion object {

        var BUNDLE_POST = "post"

        fun startActivity(activity: Activity?, post: Post) {
            val intent = Intent(activity, VideoActivity::class.java)
            intent.putExtra(BUNDLE_POST, post);
            activity!!.startActivity(intent)
        }
    }

    override val fragmentContentId: Int
        get() = throw UnsupportedOperationException()
    override val layoutId: Int
        get() = R.layout.activity_video

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val post = intent.getSerializableExtra(BUNDLE_POST) as Post?
        title = post?.title

        val videoView = findViewById(R.id.video) as VideoView
        videoView.setOnPreparedListener { mp -> mp.isLooping = true }

        //TODO Refactor

        ServiceLocator.resolve(
                object : VideoPresenter.View {

                    override fun showVideo(videoFile: File) {
                        videoView.setVideoPath(videoFile.absolutePath)
                        videoView.start()
                    }

                    override fun setBusy(isBusy: Boolean) {
                        findViewById(R.id.progress).visibility = if (isBusy) View.VISIBLE else View.GONE
                    }
                }).loadVideo(post)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        else
            super.onOptionsItemSelected(item)
        return true
    }
}