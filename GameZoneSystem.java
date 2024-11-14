import java.util.*;
import java.io.*;
import java.sql.*;

abstract class User {
     GameZoneSystem gz;
     Scanner sc;

    public User(GameZoneSystem gz) {
        this.gz = gz;
    }

    public abstract void showMenu();
}

class Admin extends User {
    
    public Admin(GameZoneSystem gz) {
        super(gz);
    
    }
    Scanner sc=new Scanner(System.in);
    public void showMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("======================================================================================");
            System.out.println("======================================Admin Menu======================================");
            System.out.println("1. Add player");
            System.out.println("2. Add game");
            System.out.println("3. Add booking");
            System.out.println("4. Process payment");
            System.out.println("5. Generate player info report");
            System.out.println("6. Generate player bookings report");
            System.out.println("7. Generate user wise revenue report");
            System.out.println("8. Logout");
            System.out.println("======================================================================================");
            System.out.println("choose your action, enter a valid number to continue with that process. ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    gz.addUser();
                    break;
                case 2:
                    gz.addGame();
                    break;
                case 3:
                    gz.addBooking();
                    break;
                case 4:
                    gz.processPayment();
                    break;
                case 5:
                    gz.playerInfoReport();
                    break;
                case 6:
                    gz.playerBookingsReport();
                    break;
                case 7:
                    gz.totalRevenueReport();
                    break;
                case 8:
                    gz.setCurrentUser(null);
                    exit = true;
                    System.out.println("you are now logged out.");
                    break;
                default:
                    System.out.println("this isn't a valid number. please enter a valid one");
                    break;
            }
        }
    }
}

class Player extends User {
    public Player(GameZoneSystem gz) {
        super(gz);
    }
    Scanner sc=new Scanner(System.in);
    public void showMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("======================================================================================");
            System.out.println("=====================================Player Menu======================================");
            System.out.println("1. Add booking");
            System.out.println("2. Process payment");
            System.out.println("3. Logout");
            System.out.println("======================================================================================");
            System.out.println("choose your action, enter a valid number to continue with that process. ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    gz.addBooking();
                    break;
                case 2:
                    gz.processPayment();
                    break;
                case 3:
                    gz.setCurrentUser(null);
                    exit = true;
                    System.out.println("you are logged out now.");
                    break;
                default:
                    System.out.println("this isn't a valid number. please enter a valid one");
                    break;
            }
        }
    }
}

class GameZoneSystem{
    Connection con;
    Scanner sc=new Scanner(System.in);
    String currentUser;
    boolean isAdmin;
    User user;

    public void run() {
        try {
            String url = "jdbc:mysql://localhost:3306/gamezone";
            String dbuser = "root";
            String dbpass = "";
            String driverName = "com.mysql.cj.jdbc.Driver";
            Class.forName(driverName);
            con = DriverManager.getConnection(url, dbuser, dbpass);

            sc = new Scanner(System.in);
            boolean exit = false;
            
            while (!exit) {
                if (currentUser == null) {
                    System.out.println("======================================================================================");
                    System.out.println("=====================================Main Menu======================================");
                    System.out.println("Choose an action:");
                    System.out.println("1. Login");
                    System.out.println("2. Register");
                    System.out.println("3. Exit");
                    System.out.println("======================================================================================");
                    System.out.println("choose your action, enter a valid number to continue with that process. ");
                    int choice = sc.nextInt();
                    sc.nextLine();
                    switch (choice) {
                        case 1:
                            login();
                            break;
                        case 2:
                            register();
                            break;
                        case 3:
                            exit = true;
                            break;
                        default:
                            System.out.println("this isn't a valid number. please enter a valid one");
                            break;
                    }
                } else {
                    user.showMenu();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void login() {
        System.out.println("Enter username:");
        String username = sc.nextLine();
        System.out.println("Enter password:");
        String password = sc.nextLine();

        try {
            String query = "select profile from users where username = ? and password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                currentUser = username;
                isAdmin = "admin".equals(rs.getString("profile"));
                if (isAdmin) {
                    user =(User) new Admin(this);
                } else {
                    user =(User) new Player(this);
                }
                System.out.println("Congratulation! Login Successful. \nWelcome " + (isAdmin ? "Admin" : "Player") + " " + currentUser);
            } else {
                System.out.println("invalid username or password, cannot login.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void register() {
        System.out.println("Enter username:");
        String username = sc.nextLine();
        System.out.println("Enter password:");
        String password = sc.nextLine();
        System.out.println("Enter profile (admin/player):");
        String profile = sc.nextLine();

        try {
            String query = "insert into users (username, password, profile) values (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, profile);
            ps.executeUpdate();
            System.out.println("User is registered successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUser() {
        System.out.println("Enter username:");
        String username = sc.nextLine();
        System.out.println("Enter password:");
        String password = sc.nextLine();
        System.out.println("Enter profile(you can make player profile and admin profile):");
        String profile = sc.nextLine();

        try {
            String query = "insert into users (username, password, profile) values (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, profile);
            ps.executeUpdate();
            System.out.println("Process successful. "+profile+" added to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addGame() {
        System.out.println("Enter game title:");
        String title = sc.nextLine();
        System.out.println("Enter game type:");
        String type = sc.nextLine();
        System.out.println("Is the game available (true/false):");
        boolean availability = sc.nextBoolean();
        sc.nextLine();
        try {
            String query = "insert into games (title, type, availability) values (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, type);
            ps.setBoolean(3, availability);
            ps.executeUpdate();
            System.out.println("Game is now added to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addBooking(){
        System.out.println("Enter username:");
        String username = sc.nextLine();
        System.out.println("Enter game title:");
        String gameTitle = sc.nextLine();

        try {
            String checkGame = "select availability from games where title = ?";
            PreparedStatement pst = con.prepareStatement(checkGame);
            pst.setString(1, gameTitle);
            ResultSet gameResultSet = pst.executeQuery();

            if (gameResultSet.next()) {
                boolean isAvailable = gameResultSet.getBoolean(1);

                if (isAvailable) {
                    System.out.println("Enter booking date (yyyy-mm-dd):");
                    String dateStr = sc.nextLine();
                    System.out.println("Enter booking time:");
                    String time = sc.nextLine();

                    java.sql.Date date = java.sql.Date.valueOf(dateStr);

                    String query = "insert into bookings (username, gameTitle, date, time) values (?, ?, ?, ?)";
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, username);
                    ps.setString(2, gameTitle);
                    ps.setDate(3, date);
                    ps.setString(4, time);
                    ps.executeUpdate();
                    System.out.println("Booking added to database.");
                } else {
                    System.out.println("Game is not available for booking.");
                }
            } else {
                System.out.println("The game you entered does not exist. Please enter a valid game.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
    }

    public void processPayment() {
        System.out.println("Enter username:");
        String username = sc.nextLine();
        System.out.println("Enter amount:");
        double amount = sc.nextDouble();
        sc.nextLine();
        System.out.println("Enter payment method:");
        String paymentMethod = sc.nextLine();

        try {
            String query = "insert into payments (username, amount, paymentMethod) values (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setDouble(2, amount);
            ps.setString(3, paymentMethod);
            ps.executeUpdate();
            System.out.println("Payment processed and added to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void totalRevenueReport() {
        try {
            String query = "select username, sum(amount) as total_revenue from payments group by username";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            Map<String, Double> userRevenueMap = new HashMap<>();
            while (rs.next()) {
                String username = rs.getString("username");
                double totalRevenue = rs.getDouble("total_revenue");
                userRevenueMap.put(username, totalRevenue);
            }

            try (FileWriter fw = new FileWriter("user_wise_revenue_report.txt");
                 BufferedWriter writer = new BufferedWriter(fw)) {
                for (Map.Entry<String, Double> entry : userRevenueMap.entrySet()) {
                    writer.write("User: " + entry.getKey() + ", Total Revenue Generated: " + entry.getValue() + " Rs");
                    writer.newLine();
                }
                System.out.println("User-wise revenue report generated.");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void playerInfoReport() {
        List<String> playerInfoList = new ArrayList<>();
        try {
            String query = "select id, username, profile from users where profile='player'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = rs.getString("username");
                playerInfoList.add("player ID: " + id + ", player username: " + username);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("player_info_report.txt"))) {
                for (String info : playerInfoList) {
                    writer.write(info);
                    writer.newLine();
                }
            }

            System.out.println("Player info report generated.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }

    public void playerBookingsReport() {
        List<String> bookingList = new LinkedList<>();
        try {
            String query = "select * from bookings";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String username = rs.getString("username");
                String gameTitle = rs.getString("gameTitle");
                String date = rs.getString("date");
                String time = rs.getString("time");
                bookingList.add("Player username: " + username + " Game title: " + gameTitle + " date: " + date + " timing: "+ time);
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("player_bookings_report.txt"))) {
                for (String booking : bookingList) {
                    writer.write(booking);
                    writer.newLine();
                }
            }

            System.out.println("Player bookings report generated.");        
        } catch (Exception e) {
            e.printStackTrace();
        }
   
    }

    public void setCurrentUser(String username) {
        this.currentUser = username;
    }

    public static void main(String[] args) {
        GameZoneSystem gz = new GameZoneSystem();
        gz.run();
    }
}