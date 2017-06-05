package servicio;

import java.util.*;

import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.*;

/** This class demonstrates building and using a Stanford CoreNLP pipeline. 
 * 
 * @author LenovoY50
 *
 */
public class StanfordSentimentAnalyzer {

//  /** Usage: java -cp "*" StanfordCoreNlpDemo [inputFile [outputTextFile [outputXmlFile]]] */
//  public static void main(String[] args) throws IOException {
//	  
//	  String line = "I love you.";
//	  System.out.println( getSentiment(line) );
//	  line = "I hate you.";
//	  System.out.println( getSentiment(line) );
//  }
//  

  public static String getSentiment(String line) {

	    String sentiment = null;

	    // Create a CoreNLP pipeline. To build the default pipeline, you can just use:
	    //   StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	    // Here's a more complex setup example:
	    //   Properties props = new Properties();
	    //   props.put("annotators", "tokenize, ssplit, pos, lemma, ner, depparse");
	    //   props.put("ner.model", "edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz");
	    //   props.put("ner.applyNumericClassifiers", "false");
	    //   StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	    // Add in sentiment
	    Properties props = new Properties();
	    props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");

	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	    // Initialize an Annotation with some text to be annotated. The text is the argument to the constructor.
	    Annotation annotation;
	    //annotation = new Annotation("Things are bad. The world is bad. Everything is wrong. But it would be all worse if I were bad too.");
	    annotation = new Annotation(line);
	    
	    // run all the selected Annotators on this text
	    pipeline.annotate(annotation);

	    // this prints out the results of sentence analysis to file(s) in good formats
	    //pipeline.prettyPrint(annotation, out);
//	    if (xmlOut != null) {
//	      pipeline.xmlPrint(annotation, xmlOut);
//	    }

	    // An Annotation is a Map with Class keys for the linguistic analysis types.
	    // You can get and use the various analyses individually.
	    // For instance, this gets the parse tree of the first sentence in the text.
	    List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
	    if (sentences != null && ! sentences.isEmpty()) {
	      CoreMap sentence = sentences.get(0);
	      sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
	      //out.println(sentiment);
	    }
	  
	    return sentiment;
  }
  
}

