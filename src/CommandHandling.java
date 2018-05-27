import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommandHandling {

    static void treat(String command, CopyOnWriteArrayList<Pj> collection, ObjectOutputStream out, ObjectInputStream in) throws IOException {
        String[] commands = {"sort", "show", "s", "size", "rl", "rg",
                "rv", "out", "help", "q", "in"};
        int i;
        for (i = 0; i < commands.length; i++) if (command.startsWith(commands[i])) break;
        switch (i) {
            case 0:
                PjCollection.pjeysSrt(collection);
                out.writeObject("sorted");
                break;
            case 1:
                PjCollection.show(collection, out);
                break;
            case 2:
                Scenary.starting(out, in, collection);

                break;
            case 3:
                    out.writeObject(collection.size());
                break;
            case 4:
                PjCollection.getElemByString(command);
                PjCollection.removeLower(PjCollection.pj_save, collection);
                break;
            case 5:
                PjCollection.getElemByString(command);
                PjCollection.removeGreater(PjCollection.pj_save, collection);
                break;
            case 6:
                PjCollection.getElemByString(command);
                PjCollection.remove(PjCollection.pj_save, collection);
                break;
            case 7:
//                String path_save = "C:\\Users\\chist\\Documents\\itmo\\proga\\Labbbb\\src\\Output.txt";
//                String filename = collection.toString();
                String path_save = "D:\\0����\\����������������(���)\\6\\Labbbb\\src\\Output.txt";
                Output.save(path_save, collection);
                out.writeObject("saved");
                break;
            case 8:
                out.writeObject(Scenary.help());
                break;
            case 9:
                System.exit(0);
                break;
            case 10:
//                String path = "C:\\Users\\chist\\Documents\\itmo\\proga\\Labbbb\\src\\form.xml";
                String path = "D:\\0����\\����������������(���)\\6\\Labbbb\\src\\form.xml";
                In.getPjeys(path, collection);
                PjCollection.show(collection, out);
                break;
            default:
                System.out.println("command is Illegal");
        }

    }

}
