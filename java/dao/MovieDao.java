package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Order;
import model.Employee;
import model.Movie;

public class MovieDao {


	public List<Movie> getMovies() {

		/*
		 * The students code to fetch data from  the database will be written here
		 * Query to fetch details of all the movies has to be implemented
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" List
		 */

		List<Movie> movies = new ArrayList<Movie>();

		/*Sample data begins*/

		Connection con = null;
		try {
			con = DBHelper.getInstance().getConnection();

			String query = 
					"SELECT  * FROM  Movie";

			ResultSet rs = null;
			rs = DBHelper.getInstance().executeQuery(query, con);

			if(rs == null) {
				System.out.println("empty");
			}

			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("Id"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movie.setDistFee(rs.getInt("DistrFee"));
				movie.setNumCopies(rs.getInt("NumCopies"));
				movie.setRating(rs.getInt("Rating"));
				movies.add(movie);
			}

		} catch (SQLException e) {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return movies;
	}

	public Movie getMovie(String movieID) {

		/*
		 * The students code to fetch data from the database based on "movieID" will be written here
		 * movieID, which is the Movie's ID who's details have to be fetched, is given as method parameter
		 * The record is required to be encapsulated as a "Movie" class object
		 */

		Movie movie = null;

		/* Sample data begins */

		Connection con = null;
		try {
			con = DBHelper.getInstance().getConnection();

			String query = "SELECT * FROM Movie WHERE Id = '" + movieID + "'";

			ResultSet rs = DBHelper.getInstance().executeQuery(query, con);

			if (rs.next()) {
				movie = new Movie();
				movie.setMovieID(rs.getInt("Id"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movie.setDistFee(rs.getInt("DistrFee"));
				movie.setNumCopies(rs.getInt("NumCopies"));
				movie.setRating(rs.getInt("Rating"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		/* Sample data ends */

		return movie;
	}

	public String addMovie(Movie movie) {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement getMaxIdStmt = null;
		PreparedStatement insertStmt = null;

		try {
			con = DBHelper.getInstance().getConnection();

			String getMaxIdQuery = 
					"SELECT MAX(Id) FROM cse305_2.Movie";
			getMaxIdStmt = con.prepareStatement(getMaxIdQuery);
			rs = getMaxIdStmt.executeQuery();

			int newMovieID = 1; 
			if (rs.next()) {
				newMovieID = rs.getInt(1) + 1; 
			}

			String insertQuery = "INSERT INTO cse305_2.Movie (Id, Name, Type, Rating, DistrFee, NumCopies) VALUES (?, ?, ?, ?, ?, ?)";
			insertStmt = con.prepareStatement(insertQuery);
			insertStmt.setInt(1, newMovieID);
			insertStmt.setString(2, movie.getMovieName());
			insertStmt.setString(3, movie.getMovieType());
			insertStmt.setInt(4, movie.getRating());
			insertStmt.setDouble(5, movie.getDistFee());
			insertStmt.setInt(6, movie.getNumCopies());
			insertStmt.executeUpdate();

		} catch (Exception e) {
			e.printStackTrace();
			return "failure";
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (getMaxIdStmt != null) {
					getMaxIdStmt.close();
				}
				if (insertStmt != null) {
					insertStmt.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "success";
	}

	public String editMovie(Movie movie) {
		/*
		 * All the values of the edit movie form are encapsulated in the movie object.
		 * These can be accessed by getter methods (see Movie class in model package).
		 * e.g. movieName can be accessed by movie.getMovieName() method.
		 * The sample code returns "success" by default.
		 * You need to handle the database update and return "success" or "failure" based on result of the database update.
		 */
		Connection con = null;



		String query = String.format(
				"UPDATE cse305_2.Movie " +
						"SET Name = '%s', Type = '%s', Rating = %d, DistrFee = %s, NumCopies = %d " +
						"WHERE Id = %d;",
						movie.getMovieName(),
						movie.getMovieType(),
						movie.getRating(),
						movie.getDistFee(),
						movie.getNumCopies(),
						movie.getMovieID());

		try {
			con = DBHelper.getInstance().getConnection();
			ResultSet rs = null;

			rs = DBHelper.getInstance().executeQuery(query, con);

			if (rs.next()) {
				return "success";
			} else {
				return "failure"; 
			}

		} catch (Exception e) {
			try {
				if (con != null)
					con.close();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			e.printStackTrace();
			return "failure";
		}
		/*Sample data begins*/

		//return "success";
		/*Sample data ends*/

	}

	public String deleteMovie(String movieID) {
		String updateRentalQuery = "UPDATE rental SET MovieId = 0 WHERE MovieId = ?";
		String deleteDependentsQuery1 = "DELETE FROM appearedin WHERE MovieId = ?";
		String deleteDependentsQuery2 = "DELETE FROM movieq WHERE MovieId = ?";
		String deleteMovieQuery = "DELETE FROM Movie WHERE Id = ?";

		try (
				Connection con = DBHelper.getInstance().getConnection();
				PreparedStatement pstmtRental = con.prepareStatement(updateRentalQuery);
				PreparedStatement pstmtDependents1 = con.prepareStatement(deleteDependentsQuery1);
				PreparedStatement pstmtDependents2 = con.prepareStatement(deleteDependentsQuery2);
				PreparedStatement pstmtMovie = con.prepareStatement(deleteMovieQuery)) {

			pstmtRental.setString(1, movieID);
			pstmtRental.executeUpdate();

			pstmtDependents1.setString(1, movieID);
			pstmtDependents1.executeUpdate();

			pstmtDependents2.setString(1, movieID);
			pstmtDependents2.executeUpdate();

			// Delete the movie
			pstmtMovie.setString(1, movieID);
			int rowsAffected = pstmtMovie.executeUpdate();

			if (rowsAffected > 0) {
				return "success";
			} else {
				System.out.println("No movie found with ID: " + movieID);
				return "failure";
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return "failure";
		}
	}


	public List<Movie> getBestsellerMovies() {

		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of the bestseller movies has to be implemented
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" List
		 */

		List<Movie> movies = new ArrayList<Movie>();

		Connection con = null;
		try {
			con = DBHelper.getInstance().getConnection();

			String query = "SELECT \n"
					+ "    Id,\n"
					+ "    Name,\n"
					+ "    Type,\n"
					+ "    Rating,\n"
					+ "    DistrFee,\n"
					+ "    NumCopies\n"
					+ "FROM \n"
					+ "    Movie\n"
					+ "ORDER BY \n"
					+ "    DistrFee DESC;";

			ResultSet rs = null;
			rs = DBHelper.getInstance().executeQuery(query, con);
			if(rs == null){
				System.out.println("Failed");
				return null;
			}
			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("Id"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movie.setDistFee(rs.getInt("DistrFee"));
				movie.setNumCopies(rs.getInt("NumCopies"));
				movie.setRating(rs.getInt("Rating"));
				movies.add(movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

			try {
				if (con != null)
					con.close();

			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return movies;
	}


	public List<Movie> getSummaryListing(String searchKeyword) {

		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch details of summary listing of revenue generated by a particular movie or movie type must be implemented
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList
		 * Store the revenue generated by an movie in the soldPrice attribute, using setSoldPrice method of each "movie" object
		 */

		List<Movie> movies = new ArrayList<Movie>();

		/*Sample data begins*/
		Connection con = null;
		try {
			con = DBHelper.getInstance().getConnection();

			String query = "SELECT *, DistrFee * NumCopies AS SoldPrice FROM Movie WHERE Name LIKE '%" + searchKeyword + "%'";

			ResultSet rs = DBHelper.getInstance().executeQuery(query, con);

			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("Id"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movie.setDistFee(rs.getInt("DistrFee"));
				movie.setNumCopies(rs.getInt("NumCopies"));
				movie.setRating(rs.getInt("Rating"));

				// Set the calculated soldPrice directly from the query
				movie.setSoldPrice(rs.getDouble("SoldPrice"));

				movies.add(movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		/* Sample data ends */

		return movies;

	}




	public List<Movie> getMovieSuggestions(String customerID) {

		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch movie suggestions for a customer, indicated by customerID, must be implemented
		 * customerID, which is the Customer's ID for whom the movie suggestions are fetched, is given as method parameter
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList
		 */

		List<Movie> movies = new ArrayList<Movie>();

		Connection con = null;
		try {
			con = DBHelper.getInstance().getConnection();
			String query = "SELECT Movie.* FROM Movie "+
					"WHERE Movie.Type IN ("+
					"SELECT M.Type FROM Movie M, Rental R , Account A "+
					"WHERE M.Id = R.MovieId AND R.AccountId = A.Id AND A.Customer = '"+customerID+"') "+
					"ORDER BY Rating DESC";

			ResultSet rs = null;
			rs = DBHelper.getInstance().executeQuery(query, con);

			if(rs == null){
				System.out.println("Failed");
				return null;
			}
			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("Id"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movie.setDistFee(rs.getInt("DistrFee"));
				movie.setNumCopies(rs.getInt("NumCopies"));
				movie.setRating(rs.getInt("Rating"));
				movies.add(movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return movies;


	}

	public List<Movie> getCurrentMovies(String customerID){

		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch currently hold movie for a customer, indicated by customerID, must be implemented
		 * customerID, which is the Customer's ID for whom currently hold movie are fetched, is given as method parameter
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList
		 */

		List<Movie> movies = new ArrayList<Movie>();
		/*Sample data begins*/
		//		for (int i = 0; i < 4; i++) {
		//			Movie movie = new Movie();
		//			movie.setMovieID(1);
		//			movie.setMovieName("The Godfather");
		//			movies.add(movie);
		//		}
		/*Sample data ends*/
		Connection con = null;
		try {
			con = DBHelper.getInstance().getConnection();

			// Query to fetch currently held movies for a customer
			String query = "SELECT M.Id, M.Name, M.Type, M.DistrFee, M.NumCopies, M.Rating " +
					"FROM Movie M " +
					"JOIN Rental R ON M.Id = R.MovieId " +
					"JOIN RentalOrder RO ON R.OrderId = RO.Id " +
					"JOIN Account A ON R.AccountId = A.Id " +
					"WHERE A.Customer = '" + customerID + "' AND RO.ReturnDate IS NULL";

			ResultSet rs = DBHelper.getInstance().executeQuery(query, con);

			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("Id"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movie.setDistFee(rs.getInt("DistrFee"));
				movie.setNumCopies(rs.getInt("NumCopies"));
				movie.setRating(rs.getInt("Rating"));
				movies.add(movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return movies;


	}

	public List<Movie> getQueueOfMovies(String customerID){

		/*
		 * The students code to fetch data from the database will be written here
		 * Query to fetch movie queue for a customer, indicated by customerID, must be implemented
		 * customerID, which is the Customer's ID for whom movie queue are fetched, is given as method parameter
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList
		 */

		List<Movie> movies = new ArrayList<Movie>();
		/*Sample data begins*/
		//		for (int i = 0; i < 4; i++) {
		//			Movie movie = new Movie();
		//			movie.setMovieID(1);
		//			movie.setMovieName("The Godfather");
		//			movie.setMovieType("Drama");
		//			movies.add(movie);
		//		}
		/*Sample data ends*/
		Connection con = null;
		try {
			con = DBHelper.getInstance().getConnection();

			// Query to fetch movie queue for a customer
			String query = "SELECT M.Id, M.Name, M.Type, M.DistrFee, M.NumCopies, M.Rating " +
					"FROM cse305_2.Movie M " +
					"JOIN cse305_2.MovieQ MQ ON M.Id = MQ.MovieId " +
					"JOIN cse305_2.Account A ON MQ.AccountId = A.Id " +
					"WHERE A.Customer = '" + customerID + "'";

			ResultSet rs = DBHelper.getInstance().executeQuery(query, con);

			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("Id"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movie.setDistFee(rs.getInt("DistrFee"));
				movie.setNumCopies(rs.getInt("NumCopies"));
				movie.setRating(rs.getInt("Rating"));
				movies.add(movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return movies;


	}



	//	public List getMoviesBySeller(String sellerID) {
	//		
	//		/*
	//		 * The students code to fetch data from the database will be written here
	//		 * Query to fetch movies sold by a given seller, indicated by sellerID, must be implemented
	//		 * sellerID, which is the Sellers's ID who's movies are fetched, is given as method parameter
	//		 * The bid and order details of each of the movies should also be fetched
	//		 * The bid details must have the highest current bid for the movie
	//		 * The order details must have the details about the order in which the movie is sold
	//		 * Each movie record is required to be encapsulated as a "Movie" class object and added to the "movies" List
	//		 * Each bid record is required to be encapsulated as a "Bid" class object and added to the "bids" List
	//		 * Each order record is required to be encapsulated as a "Order" class object and added to the "orders" List
	//		 * The movies, bids and orders Lists have to be added to the "output" List and returned
	//		 */
	//
	//		List output = new ArrayList();
	//		List<Movie> movies = new ArrayList<Movie>();
	//		List<Bid> bids = new ArrayList<Bid>();
	//		List<Order> orders = new ArrayList<Order>();
	//		
	//		/*Sample data begins*/
	//		for (int i = 0; i < 4; i++) {
	//			Movie movie = new Movie();
	//			movie.setMovieID(123);
	//			movie.setDescription("sample description");
	//			movie.setType("BOOK");
	//			movie.setName("Sample Book");
	//			movies.add(movie);
	//			
	//			Bid bid = new Bid();
	//			bid.setCustomerID("123-12-1234");
	//			bid.setBidPrice(120);
	//			bids.add(bid);
	//			
	//			Order order = new Order();
	//			order.setMinimumBid(100);
	//			order.setBidIncrement(10);
	//			order.setOrderID(123);
	//			orders.add(order);
	//		}
	//		/*Sample data ends*/
	//		
	//		output.add(movies);
	//		output.add(bids);
	//		output.add(orders);
	//		
	//		return output;
	//	}

	public List<Movie> getMovieTypes() {

		/*
		 * The students code to fetch data from the database will be written here
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList
		 * A query to fetch the unique movie types has to be implemented
		 * Each movie type is to be added to the "movie" object using setType method
		 */

		List<Movie> movies = new ArrayList<Movie>();

		/*Sample data begins*/
		//		movies.add(new Movie());
		//		movies.get(movies.size()-1).setMovieType("Fantasy");
		//		movies.add(new Movie());
		//		movies.get(movies.size()-1).setMovieType("Drama");
		//		movies.add(new Movie());
		//		movies.get(movies.size()-1).setMovieType("Action");
		//		movies.add(new Movie());
		//		movies.get(movies.size()-1).setMovieType("Romance");
		//		movies.add(new Movie());
		//		movies.get(movies.size()-1).setMovieType("Gore");
		//		movies.add(new Movie());
		//		movies.get(movies.size()-1).setMovieType("Comedy");

		/*Sample data ends*/
		Connection con = null;
		try {
			con = DBHelper.getInstance().getConnection();

			// Query to fetch unique movie types
			String query = "SELECT DISTINCT Type FROM Movie";

			ResultSet rs = DBHelper.getInstance().executeQuery(query, con);

			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieType(rs.getString("Type"));
				movies.add(movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return movies;
	}

	public List getMoviesByName(String movieName) {

		/*
		 * The students code to fetch data from the database will be written here
		 * The movieName, which is the movie's name on which the query has to be implemented, is given as method parameter
		 * Query to fetch movies containing movieName in their name has to be implemented
		 * Each movie's corresponding order data also has to be fetched
		 * Each movie record is required to be encapsulated as a "Movie" class object and added to the "movies" List
		 * Each order record is required to be encapsulated as a "Order" class object and added to the "orders" List
		 * The movies and orders Lists are to be added to the "output" List and returned
		 */

		List<Movie> movies = new ArrayList<Movie>();

		/*Sample data begins*/	

		Connection con = null;
		try {
			con = DBHelper.getInstance().getConnection();

			String query = "SELECT * FROM Movie WHERE NAME LIKE '%"+movieName+"%'";

			ResultSet rs = null;

			rs = DBHelper.getInstance().executeQuery(query, con);
			if(rs == null){
				System.out.println("Failed");
				return null;
			}
			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("Id"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movies.add(movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return movies;
	}

	public List getMoviesByActor(String actorName) {

		/*
		 * The students code to fetch data from the database will be written here
		 * The movieName, which is the movie's name on which the query has to be implemented, is given as method parameter
		 * Query to fetch movies containing movieName in their name has to be implemented
		 * Each movie's corresponding order data also has to be fetched
		 * Each movie record is required to be encapsulated as a "Movie" class object and added to the "movies" List
		 * Each order record is required to be encapsulated as a "Order" class object and added to the "orders" List
		 * The movies and orders Lists are to be added to the "output" List and returned
		 */

		List<Movie> movies = new ArrayList<Movie>();

		/*Sample data begins*/

		Connection con = null;
		try {

			con = DBHelper.getInstance().getConnection();

			String query = "SELECT * FROM Movie WHERE Id IN ("+
					"SELECT I.MovieId FROM AppearedIn I, Actor A WHERE A.Id = I.ActorId AND "+
					"A.Name LIKE '%"+actorName+"%')";

			ResultSet rs = null;

			rs = DBHelper.getInstance().executeQuery(query, con);

			if(rs == null){
				System.out.println("Failed");
				return null;
			}

			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("Id"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movies.add(movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return movies;
	}

	public List getMoviesByType(String movieType) {

		/*
		 * The students code to fetch data from the database will be written here
		 * The movieType, which is the movie's type on which the query has to be implemented, is given as method parameter
		 * Query to fetch movies containing movieType as their type has to be implemented
		 * Each movie's corresponding order data also has to be fetched
		 * Each movie record is required to be encapsulated as a "Movie" class object and added to the "movies" List
		 * Each order record is required to be encapsulated as a "Order" class object and added to the "orders" List
		 * The movies and orders Lists are to be added to the "output" List and returned
		 */

		List<Movie> movies = new ArrayList<Movie>();

		/*Sample data begins*/	

		Connection con = null;
		try {
			con = DBHelper.getInstance().getConnection();
			String query = "SELECT * FROM Movie WHERE Type = '" + movieType +"'";

			ResultSet rs = null;
			rs = DBHelper.getInstance().executeQuery(query, con);

			if(rs == null){
				System.out.println("Failed");
				return null;
			}

			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("Id"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movies.add(movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return movies;
	}

	public List getMovieRentalsByName(String movieName) {

		List<Movie> movies = new ArrayList<Movie>();

		Connection con = null;
		try {
			con = DBHelper.getInstance().getConnection();
			String query = "SELECT * FROM Movie WHERE Name LIKE '%"+movieName+"%'";

			ResultSet rs = null;
			rs = DBHelper.getInstance().executeQuery(query, con);

			if(rs == null){
				System.out.println("Failed");
				return null;
			}

			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("Id"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movies.add(movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return movies;
	}

	public List getMovieRentalsByCustomer(String customerName) {

		List<Movie> movies = new ArrayList<Movie>();

		/*Sample data begins*/	
		Connection con = null;
		try {
			con = DBHelper.getInstance().getConnection();

			String query = "SELECT * FROM Movie WHERE Id IN (" +
					"SELECT Q.MovieId FROM MovieQ Q, Person P, Account A " +
					"WHERE A.Id = Q.AccountId AND A.CustomerId = P.SSN AND " +
					"(P.LastName LIKE '%"+customerName+"%' " + 
					"OR P.FirstName LIKE '%"+customerName+"%'))";

			ResultSet rs = null;
			rs = DBHelper.getInstance().executeQuery(query, con);

			if(rs == null){
				System.out.println("Failed");
				return null;
			}

			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("Id"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movies.add(movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		/*Sample data ends*/
		return movies;
	}


	public List getMovieRentalsByType(String movieType) {

		List<Movie> movies = new ArrayList<Movie>();

		Connection con = null;
		try {

			con = DBHelper.getInstance().getConnection();
			String query = "SELECT * FROM Movie WHERE Type = '" + movieType +"'";

			ResultSet rs = null;

			rs = DBHelper.getInstance().executeQuery(query, con);

			if(rs == null){
				System.out.println("Failed");
				return null;
			}

			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("Id"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movies.add(movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		} finally {

			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return movies;
	}


	public List<Movie> getBestsellersForCustomer(String customerID) {

		/*
		 * The students code to fetch data from the database will be written here.
		 * Each record is required to be encapsulated as a "Movie" class object and added to the "movies" ArrayList.
		 * Query to get the Best-seller list of movies for a particular customer, indicated by the customerID, has to be implemented
		 * The customerID, which is the customer's ID for whom the Best-seller movies have to be fetched, is given as method parameter
		 */

		List<Movie> movies = new ArrayList<Movie>();

		/*Sample data begins*/
		//		for (int i = 0; i < 6; i++) {
		//			Movie movie = new Movie();
		//			movie.setMovieID(1);
		//			movie.setMovieName("The Godfather");
		//			movie.setMovieType("Drama");
		//			movie.setDistFee(10000);
		//			movie.setNumCopies(3);
		//			movie.setRating(5);
		//			movies.add(movie);
		//		}
		/*Sample data ends*/
		Connection con = null;

		try {
			con = DBHelper.getInstance().getConnection();

			String query = "SELECT m.* FROM Movie m "
					+ "JOIN customer_purchases cp ON m.Id = cp.movie_id "
					+ "WHERE cp.customer_id = '" + customerId + "' "
					+ "ORDER BY m.DistrFee DESC "
					+ "LIMIT 10";

			ResultSet rs = DBHelper.getInstance().executeQuery(query, con);
			if (rs == null) {
				System.out.println("Failed");
				return null;
			}

			while (rs.next()) {
				Movie movie = new Movie();
				movie.setMovieID(rs.getInt("Id"));
				movie.setMovieName(rs.getString("Name"));
				movie.setMovieType(rs.getString("Type"));
				movie.setDistFee(rs.getInt("DistrFee"));
				movie.setNumCopies(rs.getInt("NumCopies"));
				movie.setRating(rs.getInt("Rating"));
				movies.add(movie);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (con != null)
					con.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		return movies;
	}

}
