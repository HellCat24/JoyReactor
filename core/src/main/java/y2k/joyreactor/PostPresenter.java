package y2k.joyreactor;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by y2k on 28/09/15.
 */
public class PostPresenter {

    View view;

    public PostPresenter(View view) {
        this.view = view;

        loadComments();
        loadPost();
    }

    private void loadComments() {
        new CommentListRequest(2214973, 0)
                .request()
                .subscribe(view::updateComments, Throwable::printStackTrace);
    }

    private void loadPost() {
        ForegroundScheduler.getInstance().createWorker().schedule(() -> {
            Post post = new Post();
            post.image = "http://img0.joyreactor.cc/pics/post/-2455736.jpeg";
            post.width = 811;
            post.height = 573;

            view.updatePostImage(post);
        }, 2, TimeUnit.SECONDS);
    }

    public void selectComment(int commentId) {
        new CommentListRequest(2214973, commentId)
                .request()
                .subscribe(view::updateComments, Throwable::printStackTrace);
    }

    public interface View {

        void updateComments(List<Comment> comments);

        void updatePostImage(Post post);
    }
}