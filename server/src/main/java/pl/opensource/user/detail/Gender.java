package pl.opensource.user.detail;

public enum Gender {
	
	M("Mężczyzna"),
	F("Kobieta");
	
	
	private String descriptionPL;
	
	Gender(String descriptionPL) {
		this.descriptionPL = descriptionPL;
	}
	
	public String getDescriptionPL() {
		return descriptionPL;
	}
	
	public static void setPolishDescription(UserDetail userDetail) {
		for (Gender g: Gender.values()) {
			if (g.name().equals(userDetail.getGender())) {
				userDetail.setGender(g.getDescriptionPL());
			}
		}
	}
	
	public static void setShortCutDescription(UserDetail userDetail) {
		for (Gender g: Gender.values()) {
			if(g.getDescriptionPL().equals(userDetail.getGender())) {
				userDetail.setGender(g.name());
			}
		}
	}
}
