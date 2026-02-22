import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.*;
import java.text.DecimalFormat;
import java.util.ArrayList;


public class PizzaGUIFrame extends JFrame {
    private String crustName = "";
    private String sizeName = "Small";
    private double sizeCost = 8.00;
    private ArrayList<String> toppingsNames = new ArrayList<>();
    private double toppingsCost = 0;
    private String ingredients;

    static class pizzaCheckBox extends JCheckBox {
        private final double cost;
        private final String name;

        public pizzaCheckBox(String name, double cost) {
            super(name);
            this.name = name;
            this.cost = cost;
        }

        public double getCost() {
            return cost;
        }

        public String getName() {
            return name;
        }
    }

    static class pizzaComboBox {
        private ArrayList<Item> items;
        static class Item {
            private final double cost;
            private final String name;

            public Item(double cost, String name) {
                this.cost = cost;
                this.name = name;
            }

            public double getCost() {
                return cost;
            }

            public String getName() {
                return name;
            }

            @Override
            public String toString() {
                return name;
            }

        }
    }

    private void clearOrder(JTextArea receiptText, ButtonGroup crustGroup, JComboBox sizeBox, ArrayList<pizzaCheckBox> toppingsOptions) {
        crustName = "";
        sizeName = "Small";
        sizeCost = 8.00;
        toppingsNames.clear();
        toppingsCost = 0;
        ingredients = "";

        receiptText.setText("");
        crustGroup.clearSelection();
        sizeBox.setSelectedIndex(0);

        for (pizzaCheckBox cb : toppingsOptions) {
            cb.setSelected(false);
        }
    }

    private void quit() {
        int response = JOptionPane.showConfirmDialog(this, "\nAre you sure you want to quit?", "Quit", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    public PizzaGUIFrame() {
        this.setTitle("Pizza Order Form");
        // top row: choices of building pizza
        JPanel topRow = new JPanel();
        Font largeFont = new Font("SansSerif", Font.BOLD, 25);
        // crust panel
        JPanel crust = new JPanel(new GridBagLayout());
        crust.setBorder(new TitledBorder(new EtchedBorder(), "Crust Type"));
        JPanel crustInner = new JPanel();
        crustInner.setLayout(new BoxLayout(crustInner, BoxLayout.Y_AXIS));

        ButtonGroup crustOptions = new ButtonGroup();

        JRadioButton thin = new JRadioButton("Thin");
        thin.setFont(largeFont);
        JRadioButton regular = new JRadioButton("Regular");
        regular.setFont(largeFont);
        JRadioButton deepDish = new JRadioButton("Deep Dish");
        deepDish.setFont(largeFont);

        crustOptions.add(thin);
        crustOptions.add(regular);
        crustOptions.add(deepDish);

        ActionListener crustListener = e -> crustName = e.getActionCommand();


        thin.addActionListener(crustListener);
        regular.addActionListener(crustListener);
        deepDish.addActionListener(crustListener);

        crustInner.add(thin);
        crustInner.add(regular);
        crustInner.add(deepDish);

        crust.add(crustInner);
        // size panel
        JPanel size = new JPanel(new GridBagLayout());
        size.setBorder(new TitledBorder(new EtchedBorder(), "Pizza Size"));
        JComboBox sizeOptions = new JComboBox();
        size.add(sizeOptions);
        pizzaComboBox.Item small = new pizzaComboBox.Item(8.00, "Small");
        pizzaComboBox.Item medium = new pizzaComboBox.Item(12.00, "Medium");
        pizzaComboBox.Item large = new pizzaComboBox.Item(16.00, "Large");
        pizzaComboBox.Item superSize = new pizzaComboBox.Item(20.00, "Super");

        sizeOptions.addItem(small);
        sizeOptions.addItem(medium);
        sizeOptions.addItem(large);
        sizeOptions.addItem(superSize);
        size.add(sizeOptions);

        ActionListener sizeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pizzaComboBox.Item selected = (pizzaComboBox.Item) sizeOptions.getSelectedItem();

                if (selected != null) {
                    sizeName = selected.getName();
                    sizeCost = selected.getCost();
                }
            }
        };

        sizeOptions.addActionListener(sizeListener);
        sizeOptions.setFont(largeFont);
        sizeOptions.setPreferredSize(new Dimension(400, 40));


        // toppings panel
        JPanel toppings = new JPanel(new GridBagLayout());
        toppings.setBorder(new TitledBorder(new EtchedBorder(), "Toppings"));
        JPanel toppingsInner = new JPanel(new GridLayout(3, 2, 5, 5));
        ArrayList<pizzaCheckBox> toppingsOptions = new ArrayList<>();
        pizzaCheckBox pepperoni = new pizzaCheckBox("Pepperoni", 1.00);
        pizzaCheckBox sausage = new pizzaCheckBox("Sausage", 1.00);
        pizzaCheckBox chicken = new pizzaCheckBox("Chicken", 1.00);
        pizzaCheckBox mushrooms = new pizzaCheckBox("Mushrooms", 1.00);
        pizzaCheckBox bellPeppers = new pizzaCheckBox("Bell Peppers", 1.00);
        pizzaCheckBox olives = new pizzaCheckBox("Olives", 1.00);

        toppingsOptions.add(pepperoni);
        toppingsOptions.add(sausage);
        toppingsOptions.add(chicken);
        toppingsOptions.add(mushrooms);
        toppingsOptions.add(bellPeppers);
        toppingsOptions.add(olives);

        for (pizzaCheckBox checkBox : toppingsOptions) {
            checkBox.setFont(largeFont);
        }

        toppingsInner.add(pepperoni);
        toppingsInner.add(sausage);
        toppingsInner.add(chicken);
        toppingsInner.add(mushrooms);
        toppingsInner.add(bellPeppers);
        toppingsInner.add(olives);

        toppings.add(toppingsInner);

        topRow.setLayout(new GridLayout(1, 3, 10, 0));
        topRow.setPreferredSize(new Dimension(0, 150));
        topRow.add(crust);
        topRow.add(size);
        topRow.add(toppings);

        // middle row (receipt panel)
        JPanel receipt = new JPanel(new BorderLayout());
        receipt.setBorder(new TitledBorder(new EtchedBorder(), "Receipt"));
        JTextArea receiptText = new JTextArea();
        receiptText.setFont(new Font("Monospaced", Font.PLAIN, 20));

        receiptText.setEditable(false);
        receipt.add(receiptText);

        // bottom row (order, clear, quit)
        JPanel bottomRow = new JPanel();
        JButton order = new JButton("Order");
        JButton clear = new JButton("Clear");
        JButton quit = new JButton("Quit");

        ActionListener threeButtonActions =  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton clickedBtn = (JButton) e.getSource();

                if (clickedBtn == order) {
                    // making sure something is selected for the crust name so a blank receipt isn't printed out
                    if (crustName == null || crustName.isEmpty()) {
                        JOptionPane.showMessageDialog(PizzaGUIFrame.this,
                                "Please select a crust type before ordering!",
                                "Selection Missing",
                                JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    toppingsNames.clear();
                    toppingsCost = 0;

                    // Build the receipt string
                    StringBuilder sb = new StringBuilder();
                    sb.append("========================================\n");

                    sb.append(String.format("%-30s %10s\n",
                            sizeName + " (" + crustName + ")",
                            String.format("$%.2f", sizeCost)));

                    // Toppings Rows
                    for (pizzaCheckBox topping : toppingsOptions) {
                        if (topping.isSelected()) {
                            toppingsCost += 1.00;
                            sb.append(String.format("  + %-26s %10s\n",
                                    topping.getName(),
                                    "$1.00"));
                        }
                    }

                    double mealCost = sizeCost + toppingsCost;
                    double tax = mealCost * 0.07;
                    double totalCost = mealCost + tax;

                    sb.append("----------------------------------------\n");
                    sb.append(String.format("%-30s %10s\n", "Subtotal:", String.format("$%.2f", mealCost)));
                    sb.append(String.format("%-30s %10s\n", "Tax (7%):", String.format("$%.2f", tax)));
                    sb.append("----------------------------------------\n");
                    sb.append(String.format("%-30s %10s\n", "TOTAL:", String.format("$%.2f", totalCost)));

                    receiptText.setText(sb.toString());
                }

                else if (clickedBtn == clear) {
                    clearOrder(receiptText, crustOptions, sizeOptions, toppingsOptions);
                }

                else if (clickedBtn == quit) {
                    quit();
                }
            }
        };
        order.addActionListener(threeButtonActions);
        clear.addActionListener(threeButtonActions);
        quit.addActionListener(threeButtonActions);

        bottomRow.add(order);
        bottomRow.add(clear);
        bottomRow.add(quit);

        this.setLayout(new BorderLayout());
        this.add(topRow, BorderLayout.NORTH);
        this.add(receipt, BorderLayout.CENTER);
        this.add(bottomRow, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
