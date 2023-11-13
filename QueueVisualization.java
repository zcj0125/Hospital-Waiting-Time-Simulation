import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class QueueVisualization extends JFrame {
    private int myPosition;
    private int averageWaitTimePerPerson; // 每人平均等待时间（秒）
    private int queueLength; // 队列长度
    private Image personImage; // 用于表示队列中人的图像
    private Random random = new Random();

    public QueueVisualization(String imagePath) {
        this.queueLength = random.nextInt(10) + 1; // 生成1到10之间的随机队列长度
        this.myPosition = random.nextInt(queueLength) + 1; // 在队列中随机选择一个位置
        this.averageWaitTimePerPerson = random.nextInt(60) + 10; // 生成10到69秒之间的随机平均等待时间

        // 尝试加载图片
        try {
            this.personImage = new ImageIcon(imagePath).getImage();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "无法加载图片: " + imagePath);
            return;
        }

        this.setTitle("Queue Visualization");
        this.setSize(800, 300); // 增加窗口大小以适应较大的图片
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(new QueuePanel());
        this.setVisible(true);
    }

    class QueuePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawQueue(g);
        }

        private void drawQueue(Graphics g) {
            int x = 10;
            int imageWidth = 60; // 图片宽度
            int imageHeight = 60; // 图片高度
            for (int i = 1; i <= queueLength; i++) {
                // 绘制人物图片
                g.drawImage(personImage, x, 50, imageWidth, imageHeight, this);
                if (i == myPosition) {
                    g.drawString("you are here", x, imageHeight + 65); // 标注位置
                    int estimatedWaitTime = averageWaitTimePerPerson * (myPosition - 1);
                    g.drawString("Estimated waiting time: " + estimatedWaitTime + "sec", x, imageHeight + 85);
                }
                x += imageWidth + 10; // 图片间隔
            }
        }
    }

    public static void main(String[] args) {
        // 确保GUI更新在事件调度线程上执行
        SwingUtilities.invokeLater(() -> {
            new QueueVisualization("/Users/zhouchenjun/Documents/IdeaProjects/WatingTime/src/download.png"); // 图片路径
        });
    }
}
