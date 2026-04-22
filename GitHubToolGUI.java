import javax.swing.*;

import git.tools.client.GitSubprocessClient;

import java.awt.*;
import java.io.File;

public class GitHubToolGUI {

    private JFrame frame;
    private JTextField repoNameField;
    private JTextField descriptionField;
    private JTextArea outputArea;
    private JCheckBox privateCheckBox;
    private File selectedFolder;
    private JLabel folderLabel;

    public GitHubToolGUI() {
        frame = new JFrame("GitHub Repo Creator");
        frame.setSize(600, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //Logo and Title
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Logo
        try {
            ImageIcon logo = new ImageIcon("src/logo.png");
            JLabel logoLabel = new JLabel(logo);
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            topPanel.add(logoLabel);
        } catch (Exception e) {
            // If no logo, ignore
        }

        JLabel title = new JLabel("GitHub Repo Creator (Prototype)");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(title);

        JLabel subtitle = new JLabel("Quinnipiac x Microsoft");
        subtitle.setFont(new Font("Arial", Font.PLAIN, 12));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(subtitle);

        frame.add(topPanel, BorderLayout.NORTH);

        
        JPanel centerPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Folder selection
        JButton folderButton = new JButton("Select Folder");
        folderLabel = new JLabel("No folder selected");

        folderButton.addActionListener(e -> selectFolder());

        centerPanel.add(folderButton);
        centerPanel.add(folderLabel);

        // Repo name
        centerPanel.add(new JLabel("Repository Name:"));
        repoNameField = new JTextField();
        centerPanel.add(repoNameField);

        // Description
        centerPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        centerPanel.add(descriptionField);

        // Privacy
        centerPanel.add(new JLabel("Private Repo:"));
        privateCheckBox = new JCheckBox();
        centerPanel.add(privateCheckBox);

        // Button
        JButton createButton = new JButton("Create Repository");
        createButton.setFont(new Font("Arial", Font.BOLD, 14));
        createButton.addActionListener(e -> handleCreateRepo());

        centerPanel.add(new JLabel()); 
        centerPanel.add(createButton);

        frame.add(centerPanel, BorderLayout.CENTER);

        // OUTPUT + DISCLAIMER
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        outputArea = new JTextArea(8, 40);
        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createTitledBorder("Output"));

        JScrollPane scrollPane = new JScrollPane(outputArea);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        JLabel disclaimer = new JLabel("Prototype only - not for commercial use", SwingConstants.CENTER);
        disclaimer.setFont(new Font("Arial", Font.ITALIC, 10));
        bottomPanel.add(disclaimer, BorderLayout.SOUTH);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void selectFolder() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int result = chooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFolder = chooser.getSelectedFile();
            folderLabel.setText(selectedFolder.getName());
        }
    }

    private void handleCreateRepo() {
        String repoName = repoNameField.getText();
        String repoPath = selectedFolder.getAbsolutePath();

        if (selectedFolder == null || repoName.isEmpty()) {
            outputArea.append("Please select a folder and enter a repo name.\n");
            return;
        }

        // Creating the initial commit for the repo
        GitSubprocessClient gitSubprocessClient = new GitSubprocessClient(repoPath);

        String gitInit = gitSubprocessClient.gitInit();

        String gitAddAll = gitSubprocessClient.gitAddAll();

        String commitMessage = "Intial Commit";
        String commit = gitSubprocessClient.gitCommit(commitMessage);
        
        outputArea.append("Starting process...\n");
        outputArea.append("Repo: " + repoName + "\n");
    }

    public static void main(String[] args) {
        new GitHubToolGUI();
    }
}
