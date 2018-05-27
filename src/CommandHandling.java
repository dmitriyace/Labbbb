import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommandHandling {

    static void treat(String command, CopyOnWriteArrayList<Pj> collection, ObjectOutputStream out, ObjectInputStream in) throws IOException {
        String[] commands = {"sort", "show", "st", "size", "rl", "rg",
                "rv", "out", "h", "q", "in"};
        //                String path_save = "C:\\Users\\chist\\Documents\\itmo\\proga\\Labbbb\\src\\Output.txt";
        String path_save = "D:\\0лабы\\Программирование(вуз)\\6\\Labbbb\\src\\Output.txt";
//                String path = "C:\\Users\\chist\\Documents\\itmo\\proga\\Labbbb\\src\\form.xml";
        String path = "D:\\0лабы\\Программирование(вуз)\\6\\Labbbb\\src\\form.xml";

        int i;
        for (i = 0; i < commands.length; i++) if (command.startsWith(commands[i])) break;
        switch (i) {
            case 0:
                PjCollection.pjeysSrt(collection);
                out.writeObject("sorted");
                out.flush();
                break;
            case 1:
                PjCollection.show(collection, out);
                out.flush();
                break;
            case 2:
                Scenary.starting(out, in, collection);

                break;
            case 3:
                out.writeObject(collection.size());
                break;
            case 4:
                PjCollection.getElemByString(command);
                out.writeObject(PjCollection.removeLower(PjCollection.pj_save, collection));
//                try {
//                    Thread.sleep(20);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                out.writeObject(collection.size());
                break;
            case 5:
                PjCollection.getElemByString(command);
                out.writeObject(PjCollection.removeGreater(PjCollection.pj_save, collection));
                break;
            case 6:
                PjCollection.getElemByString(command);
                out.writeObject(PjCollection.remove(PjCollection.pj_save, collection));
                break;
            case 7:
                Output.save(path_save, collection);
                out.writeObject("saved");
                break;
            case 8:
                out.writeObject(Scenary.help());
                break;
//            case 9:
//                System.exit(0);
//                break;
            case 10:
                In.getPjeys(path, collection);
                PjCollection.show(collection, out);
                break;
            default:
                System.out.println("command is Illegal");
        }

    }

}
