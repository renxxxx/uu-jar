package com.giveup;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Comparator;

import org.apache.commons.io.IOUtils;

public class Demo {

	public static class Student implements Comparator<Student> {
		int id;
		double score;
		int rank;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public double getScore() {
			return score;
		}

		public void setScore(double score) {
			this.score = score;
		}

		public int getRank() {
			return rank;
		}

		public void setRank(int rank) {
			this.rank = rank;
		}

		@Override
		public int compare(Student o1, Student o2) {
			if (o1.getScore() < o2.getScore())
				return 1;
			else
				return -1;
		}

		@Override
		public String toString() {
			return "Student [id=" + id + ", score=" + score + ", rank=" + rank + "]";
		}

	}

	public static void main(String[] args) throws ParseException, InterruptedException, IOException {

		String olds = "<div class=\"mainTopText\"><p>水象》创刊号发型半年后，这些问题还没有找到答案思源28号|行间距55怎么可能那么大字体水象》创刊号发型半年后，这些问题还没有找到答案思源28号|行间距</p ><img src=\"images/es/zjl.jpg\" al\" alt=\"\"><p>";
		InputStream is = new FileInputStream(new File("C:/data/1.txt"));
		OutputStream os = new FileOutputStream(new File("C:/data/2.txt"));
		System.out.print(IOUtils.copy(is, os));
		is.close();
		System.out.println(is.available());
	}

}
