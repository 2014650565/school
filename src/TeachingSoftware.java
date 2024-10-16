import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

public class TeachingSoftware extends JFrame implements Observer {

    // 界面组件
    private JTextArea inputTextArea, outputTextArea;
    private JButton processButton;
    private JComboBox<String> methodSelector;
    private EventSystem eventSystem;

    // 主程序-子程序体系结构实现
    private void mainSubroutineMethod(String inputText) {
        outputTextArea.setText(new StringBuilder(inputText).reverse().toString());
    }

    // 面向对象体系结构实现
    private void ooMethod(String inputText) {
        KWIC kwic = new KWIC(inputText);
        outputTextArea.setText(kwic.shift());
    }

    // 事件系统体系结构实现
    private class EventSystem extends Observable {
        public void processInput(String inputText) {
            String outputText = new StringBuilder(inputText).reverse().toString();
            setChanged();
            notifyObservers(outputText);
        }
    }

    // 管道-过滤器体系结构实现
    private class PipelineFilter {
        public String process(String inputText) {
            // 示例：简单过滤器，将文本转换为大写
            return inputText.toUpperCase();
        }
    }

    private void pipelineFilterMethod(String inputText) {
        PipelineFilter filter = new PipelineFilter();
        outputTextArea.setText(filter.process(inputText));
    }

    public TeachingSoftware() {
        initializeUI();
        eventSystem = new EventSystem();
        eventSystem.addObserver(this);
    }

    private void initializeUI() {
        setTitle("经典软件体系结构教学软件");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        inputTextArea = new JTextArea("输入文本...");
        outputTextArea = new JTextArea("输出结果...");
        outputTextArea.setEditable(false);

        methodSelector = new JComboBox<>(new String[]{"主程序-子程序", "面向对象", "事件系统", "管道-过滤器"});
        processButton = new JButton("处理");

        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processInput();
            }
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(inputTextArea), BorderLayout.NORTH);
        add(new JScrollPane(outputTextArea), BorderLayout.CENTER);
        add(methodSelector, BorderLayout.WEST);
        add(processButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void processInput() {
        String selectedMethod = (String) methodSelector.getSelectedItem();
        String inputText = inputTextArea.getText();

        switch (selectedMethod) {
            case "主程序-子程序":
                mainSubroutineMethod(inputText);
                break;
            case "面向对象":
                ooMethod(inputText);
                break;
            case "事件系统":
                eventSystem.processInput(inputText);
                break;
            case "管道-过滤器":
                pipelineFilterMethod(inputText);
                break;
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof EventSystem) {
            outputTextArea.setText((String) arg);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TeachingSoftware();
            }
        });
    }
}

class KWIC {
    private String line;

    public KWIC(String line) {
        this.line = line;
    }

    public String shift() {
        return new StringBuilder(line).reverse().toString();
    }
}
