package pl.opensource.advertisement;


public enum Sport {

	Aquatics("FINA", "Fédération internationale de natation", "Sporty wodne"),
	Archery("WA", "World Archery Federation", "Łucznictwo"),
	Athletics("IAAF", "International Association of Athletics Federations", "Lekkoatletyka"),
	Badminton("BWF", "Badminton World Federation", "Badminton"),
	BaseballWBSC("WBSC", "World Baseball Softball Confederation", "Baseball"),
	Basketball("FIBA","Basketball","Koszykówka"),
	Boxing("AIBA","International Boxing Association","Boks"),
	Canoeing("ICF", "International Canoe Federation", "Kajakarstwo"),
	Cycling("UCI", "Union Cycliste Internationale", "Jazda rowerem"),
	Equestrianism("FEI", "International Federation for Equestrian Sports", "Jeździectwo"),
	Football("FIFA", "Fédération Internationale de Football Association", "Piłka nożna"),
	Golf("IGF", "International Golf Federation", "Golf"),
	Gymnastics("IFG", "International Gymnastics Federation", "Gimnastyka"),
	Handball("IHF", "International Handball Federation", "Piłka ręczna"),
	Hockey("IHF", "International Hockey Federation", "Hokej"),
	Judo("IJF", "International Judo Federation", "JUDO"),
	ModernPentathlon("UIPM","Union Internationale de Pentathlon Moderne", "Pięciobój nowoczesny"),
	RollerSports("WS", "World Skat", "Sport na rolkach"),
	Rowing("IRF","International Rowing Federation", "Wioślarstwo"),
	Rugby("WR", "World Rugby", "Rugby"),
	Sailing("ISAF", "International Sailing Federation", "Żeglarstwo"),
	Shooting("ISSF", "International Shooting Sport Federation", "Strzelanie"),
	Climbing("IFSC", "International Federation of Sport Climbing", "Wspinaczka"), 
	TableTennis("ITTF", "International Table Tennis Federation", "Tenis stołowy"),
	Taekwondo("WT", "World Taekwondo", "Taekwondo"),
	Tennis("ITF", "International Tennis Federation", "Tenis"),
	Triathlon("ITU", "International Triathlon Union", "Triathlon"),
	Volleyball("FIVB", "Fédération Internationale de Volleyball", "Siatkówka"),
	Wrestling("UWW", "United World Wrestling", "Zapasy"),
	Squash("WSF", "World Squash Federation", "Squash"),
	Other("OTH", "Another", "Inny");
	
	private String abbreviation;
	private String descriptionEN;
	private String descriptionPL;
	
	Sport(String abbreviation, String descriptionEN, String descriptionPL) {
		this.abbreviation = abbreviation;
		this.descriptionEN = descriptionEN;
		this.descriptionPL = descriptionPL;
	}
	
	public String getDesctiptionPL() {
		return descriptionPL;
	}

	public String getAbbreviation() {
		return abbreviation;
	}
}