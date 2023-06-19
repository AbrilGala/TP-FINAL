package apis;

public class Pais {
    private String abbreviation;
    private String capital;
    private String currency;
    private String name;
    private String phone;
    private int population;
    private String flag;
    private String emblem;
    private String ortographic;
    private int id;

    public Pais(String abbreviation, String capital, String currency, String name, String phone, int population, String flag, String emblem, String ortographic, int id) {
        this.abbreviation = abbreviation;
        this.capital = capital;
        this.currency = currency;
        this.name = name;
        this.phone = phone;
        this.population = population;
        this.flag = flag;
        this.emblem = emblem;
        this.ortographic = ortographic;
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public String getCapital() {
        return capital;
    }

    public String getCurrency() {
        return currency;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getPopulation() {
        return population;
    }

    public String getFlag() {
        return flag;
    }

    public String getEmblem() {
        return emblem;
    }

    public String getOrtographic() {
        return ortographic;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        String info = "";
        info += "Country name: " + name + "\nAbbreviation: " + abbreviation + "\nCapital: " + capital + "\nCurrency: " + currency + "\nPopulation: " + population + "\nPhone code: " + phone + "\nID: " + id + "\nFlag: " + flag + "\nEmblem: " + emblem + "\nOrtographic: " + ortographic + "\n";
        return info;
    }
}
