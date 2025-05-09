package Adminstuff;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import model.Car;
import model.Booking;
import controller.BookingDataController;

// run from here for this part
public class CarManagementSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            gui.HomeGUI homeGUI = new gui.HomeGUI();
            homeGUI.setVisible(true);
        });
    }
}



//car class version, in its own file
