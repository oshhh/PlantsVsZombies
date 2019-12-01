package Controller;

import java.util.*;
import java.io.*;

public class GameOverException extends Exception {
    GameOverException() {
        super("Game over");
    }
}
