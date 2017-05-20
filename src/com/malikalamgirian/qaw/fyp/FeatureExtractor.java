/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import org.w3c.dom.*;
import uk.ac.shef.wit.simmetrics.similaritymetrics.CosineSimilarity;

/**
 *
 * @author wasif
 */
public class FeatureExtractor {

    public FeatureExtractor(Properties properties) throws Exception {
        /*
         * Set state of proper Properties instance field
         */
        this.properties = properties;
        /*
         * This also sets instance variable "feature_Extracted_File_URL"
         */
        this.setFeature_Extracted_File_URL(properties);

        /*
         * call Process method
         */
        process();
    }
    /*
     * Declarations
     */
    private Properties properties;
    private String feature_Extracted_File_URL;
    // Xpath related
//    private XPathFactory afactory;
//    private XPath xpath;
//    private XPathExpression expr;

    private void process() throws Exception {
        /*
         * Local Declarations
         */
        Document unProcessed_Doc,
                processed_Doc;

        Element UnProcessed_Doc_Root,
                processed_Doc_Root,
                CosineSimilarityRaw_Element,
                LcsOfLexemes_Based_Dice_Coefficient_Element,
                LcsBasedOnTokens_Based_Dice_Coefficient_Element,
                LcsOfCharacters_Based_Cosine_Similarity_Element,
                LcsOfLexemes_Based_Cosine_Similarity_Element,
                LcsBasedOnTokens_Based_Cosine_Similarity_Element,
                LcsOfCharacters_Based_Overlap_Coefficient_Element,
                LcsOfLexemes_Based_Overlap_Coefficient_Element,
                LcsBasedOnTokens_Based_Overlap_Coefficient_Element,
                TBAL_Based_Dice_Coefficient_Element,
                LBAL_Based_Dice_Coefficient_Element,
                TBAL_Based_Cosine_Similarity_Element,
                LBAL_Based_Cosine_Similarity_Element,
                TBAL_Based_Overlap_Coefficient_Element,
                LBAL_Based_Overlap_Coefficient_Element,
                characters_Based_Length_Ratio_Element,
                token_Based_Length_Ratio_Element,
                token_Based_Length_Difference_Element,
                characters_Based_Length_Difference_Element;

        Node pair_UnProcessed,
                pair_Processed;

        NodeList pairs_Of_UnProcessed_Doc,
                pairs_Processed_Doc,
                lcsOfCharacters_NodeList,
                lcsOfLexemes_NodeList,
                lcsBasedOnTokens_NodeList,
                String1_NodeList,
                String2_NodeList,
                TBALSequenceForString1_NodeList,
                TBALSequenceForString2_NodeList,
                LBALSequenceForString1_NodeList,
                LBALSequenceForString2_NodeList,
                LcsOfCharactersBasedEventDetection_NodeList,
                LcsOfLexemesBasedEventDetection_NodeList,
                LcsBasedOnTokensBasedEventDetection_NodeList,
                TBALBasedEventDetectionBasedOnString1AndString2Sequence_NodeList,
                TBALBasedEventDetectionBasedOnString1OrString2Sequence_NodeList,
                LBALSequenceForString1BasedEventDetection_NodeList,
                LBALSequenceForString2BasedEventDetection_NodeList,
                Polarity_NodeList,
                VerbOrAdjectiveAntonymMatch_NodeList,
                VerbOrAdjectiveOrNounOrAdverbAntonymMatch_NodeList,
                IntraString1AntonymMatch_NodeList,
                IntraString2AntonymMatch_NodeList,
                IntraString1OrIntraString2AntonymMatch_NodeList,
                IntraString1AndIntraString2AntonymMatch_NodeList,
                CosineSimilarity_NodeList,
                DiceSimilarity_NodeList,
                OverlapCoefficient_NodeList,
                CosineSimilarity_Without_POS_NodeList,
                DiceSimilarity_Without_POS_NodeList,
                OverlapCoefficient_Without_POS_NodeList;

        String intraLexemeSeparatorCharacter,
                tokenizerChar,
                String1Str,
                String2Str;

        int pair_Quality,
                pair_Quality_Predictions_Successfully_Made = 0,
                pair_Quality_Predictions_Failed_Made = 0;


        try {
            System.out.println("FeaturesExtracted File URL is : " + getFeature_Extracted_File_URL());
            System.out.println("properties.getInput_XML_File_URL() is : " + properties.getInput_XML_File_URL());

            /*
             * set separator character
             */
            tokenizerChar = " ";

            /*
             * set intraLexemeSeparatorCharacter, for 'Tagged String'
             */
            intraLexemeSeparatorCharacter = "/";

            /*
             * 1.Get XML Document, for <code>properties.getInput_XML_File_URL()
             * , properties.getAntonym_Match_Detector_File_URL()</code>
             */
            unProcessed_Doc = XMLProcessor.getXMLDocumentForXMLFile(properties.getInput_XML_File_URL());
            processed_Doc = XMLProcessor.getXMLDocumentForXMLFile(properties.getAntonym_Match_Detector_File_URL());

            /*
             * 2. Get Document Element
             */
            UnProcessed_Doc_Root = unProcessed_Doc.getDocumentElement();
            processed_Doc_Root = processed_Doc.getDocumentElement();

            /*
             * 3. Select all Required NodeList
             */
            int notEquals = 0;
            pairs_Of_UnProcessed_Doc = UnProcessed_Doc_Root.getElementsByTagName("Pair");

//             Select "Raw Strings"
            String1_NodeList = processed_Doc_Root.getElementsByTagName("String1");
            String2_NodeList = processed_Doc_Root.getElementsByTagName("String2");

            /*
             * 4. loop through all pairs
             */
            for (int i = 0; i < pairs_Of_UnProcessed_Doc.getLength(); i++) {
                /*
                 * Extract "Pair"
                 */
                pair_UnProcessed = pairs_Of_UnProcessed_Doc.item(i);

                /*
                 * Extract RawStrings
                 */
                String1Str = String1_NodeList.item(i).getTextContent();
                String2Str = String2_NodeList.item(i).getTextContent();

                
                /*
                 * Create Node for CosineSimilarityRaw
                 */
                CosineSimilarityRaw_Element = unProcessed_Doc.createElement("CosineSimilarityRaw");

                /*
                 * Calculate Raw Similarity
                 * using Standard similarity metrics
                 */
                 CosineSimilarityRaw_Element.setTextContent(Float.toString((new CosineSimilarity()).getSimilarity(String1Str, String2Str)));
                 
                 /*
                  * Add pair
                  */
                pair_UnProcessed.appendChild(CosineSimilarityRaw_Element);

                System.out.println((i + 1) + " / " + pairs_Of_UnProcessed_Doc.getLength() + "\n");
            }

            /*
             * 5. Transform the tree, to "feature_Extracted_File_URL"
             */
            XMLProcessor.transformXML(unProcessed_Doc, new StreamResult(new File(this.feature_Extracted_File_URL)));

        } catch (Exception e) {
            throw new Exception("FeatureExtractor : Process :"
                    + e + " : " + e.getMessage());
        }
    }

    /*
     * Helper Methods
     */
    private String getFeature_Extracted_File_URL() {
        return feature_Extracted_File_URL;
    }

    private void setFeature_Extracted_File_URL(Properties properties) throws Exception {
        /*
         * Local Declarations
         */
        String suffixToAdd,
                newURL;
        try {
            /*
             * add Suffix, "_features_extracted" for "Features Extracted"
             */
            suffixToAdd = "cos_sim_raw_features_extracted";

            /*
             * call FileSystemManager.addSuffixToFileURL
             */
            newURL = FileSystemManager.addSuffixToFileURL(properties.getInput_XML_File_URL(), suffixToAdd, null);

            /*
             * set instance state
             */
            feature_Extracted_File_URL = newURL;

            /*
             * set "properties" instance field value
             */
            properties.setFeature_Extracted_File_URL(newURL);

        } catch (Exception e) {
            throw new Exception("FeatureExtractor : setFeature_Extracted_File_URL : "
                    + e + " : " + e.getMessage());
        }
    }

    private Float getDiceCoefficient(Float x_Common_y, Float x, Float y) throws Exception {
        /*
         * Local Declarations
         */
        Float dice_Value_To_Return = null;

        try {
            dice_Value_To_Return = (2 * x_Common_y) / (x + y);

        } catch (Exception e) {
            throw new Exception("FeatureExtractor : getDiceCoefficient : "
                    + e + " : " + e.getMessage());
        }
        return dice_Value_To_Return;
    }

    private Float getCosineSimilarity(Float x_Common_y, Float x, Float y) throws Exception {
        /*
         * Local Declarations
         */
        Float cosine_Similarity_Value_To_Return = null;

        try {
	/*
         * May need to change the definiton...
         */
            cosine_Similarity_Value_To_Return = x_Common_y / Float.valueOf(Double.toString(Math.sqrt((x * y))));

        } catch (Exception e) {
            throw new Exception("FeatureExtractor : getCosineSimilarity : "
                    + e + " : " + e.getMessage());
        }
        return cosine_Similarity_Value_To_Return;
    }

    private Float getOverlapCoefficient(Float x_Common_y, Float x, Float y) throws Exception {
        /*
         * Local Declarations
         */
        Float overlap_Coefficient_Value_To_Return = null;

        try {
            overlap_Coefficient_Value_To_Return = x_Common_y / Math.min(x, y);

        } catch (Exception e) {
            throw new Exception("FeatureExtractor : getOverlapCoefficient : "
                    + e + " : " + e.getMessage());
        }

        return overlap_Coefficient_Value_To_Return;
    }

    private Float getLengthRatio(Float x, Float y) throws Exception {
        /*
         * Local Declarations
         */
        Float length_Ratio_To_Return = null;

        try {
            length_Ratio_To_Return = Math.min(x, y) / Math.max(x, y);

        } catch (Exception e) {
            throw new Exception("FeatureExtractor : getLengthRatio : "
                    + e + " : " + e.getMessage());
        }
        return length_Ratio_To_Return;
    }

    /*
     * Here the definition of Tokens and Lexemes is same.
     */
    private Float getNumberOfTokens(String string_To_Find_Number_Of_Tokens_Of, String tokenizer_Char) throws Exception {
        /*
         * Local Declarations
         */
        Float number_Of_Tokens_To_Return = null;

        try {
            number_Of_Tokens_To_Return = (float) string_To_Find_Number_Of_Tokens_Of.split(tokenizer_Char).length;

        } catch (Exception e) {
            throw new Exception("FeatureExtractor : getNumberOfTokens : "
                    + e + " : " + e.getMessage());
        }
        return number_Of_Tokens_To_Return;
    }
}
