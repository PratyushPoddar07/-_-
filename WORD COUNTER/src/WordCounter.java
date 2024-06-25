import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WordCounter extends JFrame {

    private JTextArea textArea;
    private JButton button, clear;
    private JLabel titleLabel;
    private AbstractDocument doc;

    public WordCounter() {
        // Set up the frame
        setTitle("Word Counter");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize title label
        titleLabel = new JLabel("Word Counter", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Raleway", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(102, 123, 198)); // Set background color of title label

        // Initialize text area
        textArea = new JTextArea();
        textArea.setFont(new Font("Raleway", Font.BOLD, 14));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(new Color(253, 255, 210)); // Set background color of text area
        textArea.setMargin(new Insets(10, 10, 10, 10)); // Add margin

        // Override document filter to prepend a space to each new line
        doc = (AbstractDocument) textArea.getDocument();
        DocumentFilter spaceFilter = new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (offset == 0 || (offset > 0 && "\n".equals(fb.getDocument().getText(offset - 1, 1)))) {
                    string = " " + string;
                }
                super.insertString(fb, offset, string, attr);
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (offset == 0 || (offset > 0 && "\n".equals(fb.getDocument().getText(offset - 1, 1)))) {
                    text = " " + text;
                }
                super.replace(fb, offset, length, text, attrs);
            }
        };
        doc.setDocumentFilter(spaceFilter);

        // Initialize buttons
        button = new JButton("Count Words");
        button.setPreferredSize(new Dimension(150, 30)); // Set preferred size for button
        clear = new JButton("Clear");
        clear.setPreferredSize(new Dimension(150, 30)); // Set preferred size for clear

        // Add action listeners
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Clear text area and remove space filter
                doc.setDocumentFilter(null);
                textArea.setText("");
                doc.setDocumentFilter(spaceFilter);
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Count words and show result in popup
                String text = textArea.getText();
                int wordCount = countWords(text);
                showWordCountDialog(wordCount);
            }
        });

        // Create a panel for buttons with FlowLayout for equal size buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBackground(new Color(255, 180, 194)); // Set background color
        buttonPanel.add(button);
        buttonPanel.add(clear);

        // Create a panel for the title and set background color
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.setBackground(new Color(102, 123, 198)); // Set background color

        // Set up layout
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(102, 123, 198)); // Set background color of main frame
        add(titlePanel, BorderLayout.NORTH); // Add title panel to the top
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH); // Add button panel to the bottom
    }

    private int countWords(String text) {
        if (text == null || text.isEmpty()) {
            return 0;
        }
        // Remove the space prefix before counting words
        String sanitizedText = text.replaceAll("(?m)^\\s+", "");
        String[] words = sanitizedText.trim().split("\\s+");
        return words.length;
    }

    private void showWordCountDialog(int wordCount) {
        // Custom panel for JOptionPane with the desired background color
        JPanel panel = new JPanel();
        panel.setBackground(new Color(173, 216, 153)); // Set background color of the panel
        panel.add(new JLabel("Word Count: " + wordCount));

        JOptionPane.showMessageDialog(this,
                panel,
                "Word Count Result",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new WordCounter().setVisible(true);
            }
        });
    }
}
