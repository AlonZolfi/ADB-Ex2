package hib;
// Generated Dec 14, 2019 11:35:03 AM by Hibernate Tools 4.3.5.Final

/**
 * Similarity generated by hbm2java
 */
public class Similarity implements java.io.Serializable {

	private SimilarityId id;
	private Mediaitems mediaitemsByMid2;
	private Mediaitems mediaitemsByMid1;
	private double similarity;

	public Similarity() {
	}

	public Similarity(SimilarityId id, Mediaitems mediaitemsByMid2, Mediaitems mediaitemsByMid1, double similarity) {
		this.id = id;
		this.mediaitemsByMid2 = mediaitemsByMid2;
		this.mediaitemsByMid1 = mediaitemsByMid1;
		this.similarity = similarity;
	}

	public SimilarityId getId() {
		return this.id;
	}

	public void setId(SimilarityId id) {
		this.id = id;
	}

	public Mediaitems getMediaitemsByMid2() {
		return this.mediaitemsByMid2;
	}

	public void setMediaitemsByMid2(Mediaitems mediaitemsByMid2) {
		this.mediaitemsByMid2 = mediaitemsByMid2;
	}

	public Mediaitems getMediaitemsByMid1() {
		return this.mediaitemsByMid1;
	}

	public void setMediaitemsByMid1(Mediaitems mediaitemsByMid1) {
		this.mediaitemsByMid1 = mediaitemsByMid1;
	}

	public double getSimilarity() {
		return this.similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

}