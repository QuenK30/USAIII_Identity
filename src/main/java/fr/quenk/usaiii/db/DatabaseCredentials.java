package fr.quenk.usaiii.db;
// Class:
public class DatabaseCredentials {
    private final String host;
    private final String port;
    private final String database;
    private final String username;
    private final String password;

    public DatabaseCredentials(String host, String port, String database, String username, String password) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
    }
    public String toURI() {
        final StringBuilder sb = new StringBuilder();

        sb.append("jdbc:mysql://")
            .append(host)
            .append(":")
            .append(port)
            .append("/")
            .append(database);

        return sb.toString();
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
