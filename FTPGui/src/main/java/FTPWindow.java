import impl.FTPClient;
import impl.FTPFile;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;

class FTPWindow extends JFrame {

    private final FTPClient client;
    private TreePath pathToFile;

    FTPWindow(final String url) throws IOException {
        super("FTP");
        client = new FTPClient(url);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        final JPanel mainPanel = new JPanel();
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setLayout(new BorderLayout());

        final JTree tree = initTree();
        tree.setEditable(false);
        tree.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    downloadFile(pathToFile);
                }
            }
        });

        tree.addTreeSelectionListener(e -> pathToFile = e.getPath());
        mainPanel.add(tree);

        final JButton downloadButton = new JButton("Download");
        downloadButton.addActionListener(e -> downloadFile(pathToFile));
        mainPanel.add(downloadButton, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setSize(700, 700);
    }

    private void downloadFile(final TreePath path) {
        if (path == null) {
            showMessageDialog(null, "No file chosen");
            return;
        }
        final FTPFile targetFile =
                (FTPFile) ((DefaultMutableTreeNode) path.getLastPathComponent()).getUserObject();
        if (targetFile.isDirectory()) {
            showMessageDialog(null, "Unable to download directory");
            return;
        }
        final StringJoiner joiner = new StringJoiner(File.separator);
        for (final Object pathFile : path.getPath()) {
            joiner.add(pathFile.toString());
        }

        final JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("."));
        chooser.setDialogTitle("Chose target location");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        chooser.setAcceptAllFileFilterUsed(true);
        chooser.setApproveButtonText("Save");

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            final File destination = chooser.getSelectedFile();
            try {
                client.get(joiner.toString(),
                        destination.toString() + File.separator + targetFile.name());
            } catch (final IOException e) {
                showMessageDialog(null,
                        "Unable to download file. Canceled with error: " + e.getMessage());
            }
        }
    }

    private JTree initTree() {
        final DefaultMutableTreeNode top =
                new DefaultMutableTreeNode(new FTPFile("./", true));
        createInnerFiles(top);

        final JTree tree = new JTree(top);
        tree.getSelectionModel().setSelectionMode
                (TreeSelectionModel.SINGLE_TREE_SELECTION);

        return tree;
    }

    /**
     * The method adds all inner files of the directory to the tree.
     *
     * @param root target node
     */
    private void createInnerFiles(final DefaultMutableTreeNode root) {
        final String url = getURL(root);
        List<FTPFile> files = Collections.emptyList();
        try {
            files = client.list(url);
        } catch (final IOException e) {
            showMessageDialog(null,
                    "Fatal error happened. Application will be closed with error: " + e.getMessage());
            System.exit(1);
        }
        for (final FTPFile file : files) {
            final DefaultMutableTreeNode node =
                    new DefaultMutableTreeNode(file, file.isDirectory());
            root.add(node);
            if (file.isDirectory()) {
                createInnerFiles(node);
            }
        }
    }

    /**
     * The method get path to node.
     * */
    private static String getURL(final DefaultMutableTreeNode node) {
        final StringJoiner joiner = new StringJoiner(File.separator);
        final TreeNode[] path = node.getPath();
        for (final TreeNode pathNode : path) {
            joiner.add(pathNode.toString());
        }
        return joiner.toString();
    }
}


