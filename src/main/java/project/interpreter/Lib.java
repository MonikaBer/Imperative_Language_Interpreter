package project.interpreter;

import project.interpreter.evaluatedExpr.Box;
import project.interpreter.evaluatedExpr.EvalStructValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Lib {

    public static Box pullBox(HashMap<String, Box> vars, ArrayList<String> name) {
        if (name.size() == 0)
            return null;

        String id = name.get(0);           //get variable name
        if (!vars.containsKey(id))         //var isn't declared
            return null;

        Box box = vars.get(id);            //get box of var
        if (name.size() == 1)              //var is: bool/string/int/double/struct
            return box;

        if (!(box.getValue() instanceof EvalStructValue))  //length of var name > 1 && var isn't struct -> impossible
            return null;

        EvalStructValue struct = (EvalStructValue) box.getValue();
        for (int i = 1; i < name.size(); ++i) {
            if (!(struct.getMap().containsKey(name.get(i))))
                return null;

            box = (Box) struct.getMap().get(name.get(i));
            if (i == name.size() - 1)
                return box;

            if (!(box.getValue() instanceof EvalStructValue))
                return null;

            struct = (EvalStructValue) box.getValue();
        }

        return null;
    }
}
