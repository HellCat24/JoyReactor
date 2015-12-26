package y2k.joyreactor;

import kotlin.Unit;
import org.robovm.apple.uikit.UIButton;
import org.robovm.apple.uikit.UIImageView;
import org.robovm.apple.uikit.UILabel;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.objc.annotation.CustomClass;
import org.robovm.objc.annotation.IBOutlet;
import y2k.joyreactor.common.ServiceLocator;
import y2k.joyreactor.platform.ImageRequest;
import y2k.joyreactor.presenters.ProfilePresenter;

/**
 * Created by y2k on 9/30/15.
 */
@CustomClass("ProfileViewController")
public class ProfileViewController extends UIViewController implements ProfilePresenter.View {

    UIImageView userImage;
    UILabel userName;
    UILabel rating;
    StarProgress stars;
    ProgressBar progressToNewStar;
    UIButton logoutButton;

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();
        ProfilePresenter presenter = ServiceLocator.getInstance().provideProfilePresenter(this);
        logoutButton.addOnTouchUpInsideListener((sender, e) -> presenter.logout());
    }

    // ==========================================
    // Implement View methods
    // ==========================================

    @Override
    public void setProfile(Profile profile) {
        userName.setText(profile.getUserName());
        new ImageRequest()
                .setUrl(profile.getUserImage())
                .setSize((int) userImage.getFrame().getWidth(), (int) userImage.getFrame().getHeight())
                .to(userImage, uiImage -> {
                    userImage.setImage(uiImage);
                    return Unit.INSTANCE;
                });
        rating.setText(Translator.get("Rating: ") + profile.getRating());
        stars.setStars(profile.getStars());
        progressToNewStar.setValue(profile.getProgressToNewStar());
    }

    @Override
    public void setBusy(boolean isBusy) {
        logoutButton.setEnabled(!isBusy);
        getNavigationItem().setHidesBackButton(isBusy, true);
    }

    // ==========================================
    // Outlets
    // ==========================================

    @IBOutlet
    void setUserImage(UIImageView userImage) {
        this.userImage = userImage;
    }

    @IBOutlet
    void setUserName(UILabel userName) {
        this.userName = userName;
    }

    @IBOutlet
    void setRating(UILabel rating) {
        this.rating = rating;
    }

    @IBOutlet
    void setStars(StarProgress stars) {
        this.stars = stars;
    }

    @IBOutlet
    void setProgressToNewStar(ProgressBar progressToNewStar) {
        this.progressToNewStar = progressToNewStar;
    }

    @IBOutlet
    void setLogoutButton(UIButton logoutButton) {
        this.logoutButton = logoutButton;
    }
}