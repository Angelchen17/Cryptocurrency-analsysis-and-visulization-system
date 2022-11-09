package CoinAnalysis_login;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class credential {
	
	private static final String filepath="loginfile.txt";

	/**
	 * write password and username to file
	 * @param filepath path of file store credential
	 * @param serObj userLogin object
	 */
	public void WriteObjectToFile(String filepath, userLogin[] serObj) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            for (userLogin each : serObj)
            	objectOut.writeObject(each);
            objectOut.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

	/**
	 * check if the password and username matches
	 * @param login the userlogin input from user
	 * @return true if the credential matches, false if not
	 */
	public boolean checkCredential(userLogin login) {
		try {
			FileInputStream fileIn = new FileInputStream(filepath);
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);

			while (fileIn.available() != 0) {
				userLogin checkObject = (userLogin) objectIn.readObject();
				if (checkObject.getUsername().equals(login.getUsername())) {
					objectIn.close();
					return checkObject.getPassword().equals(login.getPassword());
				}
			}
			objectIn.close();
			return false;
			
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
            return false;
		}
		
	} // ends check credential

	/**
	 * add user and password in the file
	 * @param info need to add
	 */
	public void add_user(userLogin[] info) {

		WriteObjectToFile(filepath, info);
	}
	
}
