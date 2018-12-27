package hq.test;

import java.security.NoSuchAlgorithmException;

import hq.fad.utils.FadHelper;

public class CreatePassword {

	public CreatePassword() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println(FadHelper.encryptPassword("123456"));

	}

}
