//package y2k.joyreactor.requests;
//
//import org.jsoup.nodes.Document;
//import rx.Observable;
//import y2k.joyreactor.MessageThread;
//import y2k.joyreactor.common.ObservableUtils;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by y2k on 19/10/15.
// */
//public class MessageThreadRequest extends ArrayList<MessageThread> {
//
//    public Observable<List<MessageThread>> request() {
//        return ObservableUtils.create(() -> {
//            List<MessageThread> threads = new ArrayList<>();
//            new MessagePageIterator()
//                    .observable()
//                    .flatMap(s -> new Parser(s).parse())
//                    .filter(s -> !alreadyAdded(threads, s.userName))
//                    .forEach(threads::add);
//            return threads;
//        });
//    }
//
//    private boolean alreadyAdded(List<MessageThread> threads, String name) {
//        for (MessageThread t : threads)
//            if (t.userName.equals(name)) return true;
//        return false;
//    }
//
//    private static class Parser {
//
//        private Document document;
//
//        Parser(Document document) {
//            this.document = document;
//        }
//
//        Observable<MessageThread> parse() {
//            return Observable
//                    .from(document.select("div.messages_wr > div.article"))
//                    .map(s -> {
//                        MessageThread thread = new MessageThread();
//                        thread.userName = s.select("div.mess_from > a").text();
//                        thread.userImage = new UserImageRequest(thread.userName).execute();
//                        thread.lastMessage = s.select("div.mess_text").text();
//                        thread.date = new Date(1000 * Long.parseLong(s.select("span[data-time]").attr("data-time")));
//                        return thread;
//                    });
//        }
//    }
//}