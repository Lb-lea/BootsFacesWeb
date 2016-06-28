package net.bootsfaces.demo;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean
public class TagsBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String tags = "Rock, Jazz, Rhythm and Blues";

	private static String[] bands = {"Arch Enemy","Blind Guardian","Children of Bodom","Dimmu Borgir","Edge of Sanity",
	"Fields of the Nephilim", "Gates of Ishtar", "Holy Moses", "Iced Earth", "Jethro Tull",
	"Kreator", "Lamb of God", "Mekong Delta", "Night in Gales", "Old Dead Tree", "Persefone",
	"Running Wild", "Skyclad", "The Dillinger Escape Plan", "Theater of Tragedy", "Unleashed", "Vanden Plas", "Within Temptation", "Xystus", "Yes",
	"Zenobia",
	};

	public String getBandsCSV() {
		StringBuilder b=new StringBuilder();
		for (String s:bands) {
			if (b.length()>0) {
				b.append(",");
			}
			b.append(s);
		}
		return b.toString();
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
