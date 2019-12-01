package Controller;

import java.util.*;
import java.io.*;

public class GameWinnerException extends Exception {
    GameWinnerException() {
        super("Game Won");
    }
}
