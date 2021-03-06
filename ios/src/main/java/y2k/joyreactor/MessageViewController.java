package y2k.joyreactor;

import org.jetbrains.annotations.NotNull;
import org.robovm.apple.foundation.NSIndexPath;
import org.robovm.apple.uikit.*;
import org.robovm.objc.annotation.CustomClass;
import org.robovm.objc.annotation.IBOutlet;
import y2k.joyreactor.common.BaseUIViewController;
import y2k.joyreactor.common.ServiceLocator;
import y2k.joyreactor.presenters.MessagesPresenter;

import java.util.List;

/**
 * Created by y2k on 10/2/15.
 */
@CustomClass("MessageViewController")
public class MessageViewController extends BaseUIViewController implements MessagesPresenter.View {

    UITableView list;
    List<Message> messages;
    UITextView newMessage;
    UIButton sendButton;

    MessagesPresenter presenter;

    @Override
    public void viewDidLoad() {
        super.viewDidLoad();
        list.setDataSource(new UITableViewDataSourceAdapter() {

            @Override
            public long getNumberOfRowsInSection(UITableView tableView, long section) {
                return messages == null ? 0 : messages.size();
            }

            @Override
            public UITableViewCell getCellForRow(UITableView tableView, NSIndexPath indexPath) {
                UITableViewCell cell = tableView.dequeueReusableCell("Message");
                Message thread = messages.get(indexPath.getRow());
                cell.getTextLabel().setText(thread.getText());
                return cell;
            }
        });
        list.setDelegate(new UITableViewDelegateAdapter() {

            @Override
            public void didSelectRow(UITableView tableView, NSIndexPath indexPath) {
                UIViewController vc = getStoryboard().instantiateViewController("Messages");
                getNavigationController().pushViewController(vc, true);
            }
        });
        sendButton.addOnTouchUpInsideListener(
            (sender, e) -> presenter.reply(newMessage.getText()));

        presenter = ServiceLocator.INSTANCE.resolve(getLifeCycleService(), this);
    }

    @Override
    public void updateMessages(@NotNull List<Message> messages) {
        System.out.println("updateMessages | " + messages);
        this.messages = messages;
        list.reloadData();
    }

    @Override
    public void setBusy(boolean isBusy) {
        // TODO:
        getNavigationItem().setHidesBackButton(isBusy, true);
    }

    @Override
    public void clearMessage() {
        newMessage.setText(null);
    }

    // ==========================================
    // Outlets
    // ==========================================

    @IBOutlet
    void setList(UITableView list) {
        this.list = list;
    }

    @IBOutlet
    void setNewMessage(UITextView newMessage) {
        this.newMessage = newMessage;
    }

    @IBOutlet
    void setSendButton(UIButton sendButton) {
        this.sendButton = sendButton;
    }
}