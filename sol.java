package misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class main_misc {
	
	
	public static final int MAX = 1_000_000;
	
	
	public static HashMap<String,List<Integer>> cache = new HashMap<>();
	
	public static void main(String[] args) {
		
		
		String text = "Most companies conduct their interviews in very similar ways. We will offer an overview of how companies \r\n"
				+ "interview and what they're looking for. This information should guide your interview preparation and your \r\n"
				+ "reactions during and after the interview. \r\n"
				+ "Once you are selected for an interview, you usually go through a screening interview. This is typically \r\n"
				+ "conducted over the phone. College candidates who attend top schools may have these interviews in-person. \r\n"
				+ "Don't let the name fool you; the \"screening\" interview often involves coding and algorithms questions, and \r\n"
				+ "the bar can be just as high as it is for in-person interviews. If you're unsure whether or not the interview will \r\n"
				+ "be technical, ask your recruiting coordinator what position your interviewer holds (or what the interview \r\n"
				+ "might cover). An engineer will usually perform a technical interview. \r\n"
				+ "Many companies have taken advantage of online synchronized document editors, but others will expect \r\n"
				+ "you to write code on paper and read it back over the phone. Some interviewers may even give you \"homework\"to solve after you hang up the phone or just ask you to email them the code you wrote. \r\n"
				+ "You typically do one or two screening interviewers before being brought on-site. \r\n"
				+ "In an on-site interview round, you usually have 3 to 6 in-person interviews. One of these is often over lunch. \r\n"
				+ "The lunch interview is usually not technical, and the interviewer may not even submit feedback. This is a \r\n"
				+ "good person to discuss your interests with and to ask about the company culture. Your other interviews will \r\n"
				+ "be mostly technical and will involve a combination of coding, algorithm, design/architecture, and behavioral/experience questions. \r\n"
				+ "The distribution of questions between the above topics varies between companies and even teams due to \r\n"
				+ "company priorities, size, and just pure randomness. Interviewers are often given a good deal of freedom in \r\n"
				+ "their interview questions. \r\n"
				+ "After your interview, your interviewers will provide feedback in some form. In some companies, your interviewers meet together to discuss your performance and come to a decision. In other companies, interviewers submit a recommendation to a hiring manager or hiring committee to make a final decision. In \r\n"
				+ "some companies, interviewers don't even make the decision; their feedback goes to a hiring committee to \r\n"
				+ "make a decision. \r\n"
				+ "Most companies get back after about a week with next steps (offer, rejection, further interviews, or just an \r\n"
				+ "update on the process). Some companies respond much sooner (sometimes same day!) and others take \r\n"
				+ "much longer. \r\n"
				+ "If you have waited more than a week, you should follow up with your recruiter. If your recruiter does not \r\n"
				+ "respond, this does not mean that you are rejected (at least not at any major tech company, and almost any ";
	
		
		
		String word1 = "companies";
		String word2 =  "interview";
		
		System.out.println(word_dist(word1,word2,text));
		
		
	}
	
	private static double abs(int x) {
		
		return Math.sqrt(Math.pow(x, 2));
		
	}
	
	private static String get_word(String text,int offset,boolean reverse,int[] ptr_i){
		
		StringBuilder word = new StringBuilder();
		int len = text.length();
		int c = offset;
		if(!reverse){
			
			while(!text.subSequence(c, c+1).equals(" ") || c+1 >= len){
				
				word.append(text.substring(c,c+1));
				c++;
				
			}
			ptr_i[0] = c-1;
			return word.toString();
		}
		
		while(!text.substring(c-1,c).equals(" ") || c < 0){
			
			word.append(text.substring(c-1,c));
			c--;
		}
		ptr_i[0] = c+1;
		return word.reverse().toString();
		
	}

	private static int find_dist(String word1,String word2,String text){
		
		//nowing that word1 is on the cache,starting from it find word2
		
		int strt = cache.get(word1).get(0);
		int len = text.length();
		int num_word1 = cache.get(word1).get(1);
		//search -->
		int c = 0;
		int[] i = {strt};
		for(i[0] = strt ;i[0] < len;i[0]++){
			
			String s = text.substring(i[0],i[0]+1);
			
			if(s.equals(" ")) {
				String word = get_word(text,i[0]+1,false,i);
				if(word.equals(word2)){
					cache.put(word2,new ArrayList<>(Arrays.asList(i[0]+1,c)));
					return (int)abs(num_word1 - c);
				}
				c++;
			}
		}
		//<--- search
		c = 0;
		i[0] = strt;
		for(i[0] = strt;i[0] >= 0;i[0]--){
			
			String s = text.substring(i[0]-1,i[0]);
			
			if(s.equals(" ")){
				String word = get_word(text,i[0]+1,true,i);
				if(word.equals(word2)){
					cache.put(word2,new ArrayList<>(Arrays.asList(i[0]+1,c)));
					return (int)abs(num_word1 - c);
				}
				c++;
			}
			
		}
		return -1;
		
	}
	
	private static int word_dist(String word1,String word2,String text){
		
		
		if(cache.containsKey(word1) && cache.containsKey(word2)){
			
			return (int)abs(cache.get(word1).get(1)-cache.get(word2).get(1)); 
		}
		
		if(cache.containsKey(word1)){
			
			return find_dist(word1,word2,text);
			
		}
		if(cache.containsKey(word2)){
			return find_dist(word2,word1,text);
		}	
		
		//starting from zero
		else {
			
			int c = 0;
			int[] i = {0};
			for(i[0] = 0;i[0] < text.length();i[0]++){
	
				String s = text.substring(i[0],i[0] + 1);
				
				//i can increase i in the function to optimize,avoid duplicate work
				String word = "";
				if(i[0] == 0){
					word = get_word(text,i[0],false,i);
					c++;
				}
				if(s.equals(" ")) {
					word = get_word(text,i[0]+1,false,i);
					c++;
				}
				
				/*In this case,only the first for loop of the find_dist function will be used
				 */
				
				if(word.equals(word1)){
					cache.put(word1,new ArrayList<>(Arrays.asList(i[0]+1,c-1)));
					return find_dist(word1,word2,text);
				}
				if(word.equals(word2)){
					cache.put(word2,new ArrayList<>(Arrays.asList(i[0]+1,c-1)));
					return find_dist(word2,word1,text);
				}
				
			}
			
		}
		return -1;
		
	}
		
		
}
