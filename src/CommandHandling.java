import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommandHandling {

    static void treat(String command, CopyOnWriteArrayList<Pj> collection, ObjectOutputStream out, ObjectInputStream in) throws IOException {
        String[] commands = {"sort", "load", "st", "size", "rl", "rg",
                "rv", "out", "h", "q", "in"};

        File file = new File(".\\form.xml");
        String path = file.getAbsolutePath();
        File file_save = new File(".\\Output.out");
        String path_save = file_save.getAbsolutePath();
        int i;
        for (i = 0; i < commands.length; i++) if (command.startsWith(commands[i])) break;
        switch (i) {
            case 0:
                PjCollection.pjeysSrt(collection);
                break;
            case 1:
                PjCollection.show(collection, out);
                out.flush();
                break;
            case 2:
                Scenary.starting(out, in, collection);

                break;
            case 4:
                PjCollection.getElemByString(command);
                out.writeObject(PjCollection.removeLower(PjCollection.pj_save, collection));
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
                break;
            case 8:
                out.writeObject(Scenary.help());
                break;
            case 10:
                In.getPjeys(path, collection);
                PjCollection.show(collection, out);
                break;
            default:
                System.out.println("command is Illegal");
        }

    }

}
