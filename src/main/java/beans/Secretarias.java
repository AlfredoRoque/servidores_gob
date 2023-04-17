package beans;

public enum Secretarias {

	SRE("sre"),
	CONAGUA("conagua"),
	CONAFOR("conafor"),
	CJEF("cjef"),
	CONADIS("conadis"),
	AGRICULTURA("agricultura"),
	BIENESTAR("bienestar"),
	SCT("sct"),
	CULTURA("cultura"),
	SEDATU("sedatu"),
	SE("se"),
	SENER("sener"),
	SEMARNAT("semarnat"),
	SALUD("salud"),
	SSPC("sspc"),
	CORREOS("correosdemexico"),
	
	SEGOB("segob"),
	PLICIAFEDERAL("policiafederal"),
	INM("inm"),
	SEDENA("sedena"),
	SEMAR("semar"),
	UNIVERSIDADNAVAL("universidadnaval"),
	SHCP("shcp"),
	CREZCAMOSJUNTOS("crezcamosjuntos"),
	SEP("sep"),
	CONADE("conade"),
	SFP("sfp"),
	STPS("stps"),
	SECTUR("sectur");

    private Secretarias(String label) {
        this.label = label;
    }

    public String label;

    public String getLabel() {
        return label;
    }
	
}
