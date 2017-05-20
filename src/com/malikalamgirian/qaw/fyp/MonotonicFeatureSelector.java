/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.malikalamgirian.qaw.fyp;

import java.io.BufferedWriter;
import org.w3c.dom.*;

/**
 *
 * @author wasif
 */
public class MonotonicFeatureSelector {

    public MonotonicFeatureSelector(Properties properties) throws Exception {
        /*
         * Set state of proper Properties instance field
         */
        this.properties = properties;
        /*
         * This also sets instance variable "monotonic_Features_Selected_File_URL"
         */
        this.setMonotonic_Features_Selected_File_URL(properties);

        /*
         * call Process method
         */
        process();
    }
    /*
     * Declarations
     */
    private Properties properties;
    private String monotonic_Features_Selected_File_URL;

    /*
     * This Method creates a text file, and adds "Monotonic Alignment Based
     * Feature Vectors, to that.
     */
    private void process() throws Exception {
        /*
         * Local Declarations
         */
        Document doc;

        Element root;

        String LcsOfCharacters_Based_Dice_Coefficient_String,
                LcsOfLexemes_Based_Dice_Coefficient_String,
                LcsBasedOnTokens_Based_Dice_Coefficient_String,
                TBAL_Based_Dice_Coefficient_String,
                LBAL_Based_Dice_Coefficient_String,
                characters_Based_Length_Ratio_String,
                token_Based_Length_Ratio_String,
                CharactersBasedLengthDifferenceOfContentVectors_String,
                TokenBasedLengthDifferenceOfContentVectors_String,
                LcsOfCharactersBasedEventDetection_String,
                LcsOfLexemesBasedEventDetection_String,
                LcsBasedOnTokensBasedEventDetection_String,
                
                LcsOfCharactersBasedCosineSimilarity_String,
                LcsOfLexemesBasedCosineSimilarity_String,
                LcsBasedOnTokensBasedCosineSimilarity_String,

                LcsOfCharactersBasedOverlapCoefficient_String,
                LcsOfLexemesBasedOverlapCoefficient_String,
                LcsBasedOnTokensBasedOverlapCoefficient_String,
                
                VerbOrAdjectiveAntonymMatch_String,
                VerbOrAdjectiveOrNounOrAdverbAntonymMatch_String,
                IntraString1OrIntraString2AntonymMatch_String,
                Polarity_String,
                CosineSimilarity_String,
                DiceSimilarity_String,
                OverlapCoefficient_String,
                CosineSimilarity_Without_POS_String,
                DiceSimilarity_Without_POS_String,
                CosineSimilarityRaw_String;

        Node pair,
                pair_Processed;

        NodeList pairs,
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
                CosineSimilarityRaw_NodeList,
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
                LcsOfCharactersBasedDiceCoefficient_NodeList,
                LcsOfLexemesBasedDiceCoefficient_NodeList,
                LcsBasedOnTokensBasedDiceCoefficient_NodeList,

                LcsOfCharactersBasedCosineSimilarity_NodeList,
                LcsOfLexemesBasedCosineSimilarity_NodeList,
                LcsBasedOnTokensBasedCosineSimilarity_NodeList,

                LcsOfCharactersBasedOverlapCoefficient_NodeList,
                LcsOfLexemesBasedOverlapCoefficient_NodeList,
                LcsBasedOnTokensBasedOverlapCoefficient_NodeList,

                CharactersBasedLengthRatioOfContentVectors_NodeList,
                TokenBasedLengthRatioOfContentVectors_NodeList,
                CharactersBasedLengthDifferenceOfContentVectors_NodeList,
                TokenBasedLengthDifferenceOfContentVectors_NodeList,
                CosineSimilarity_NodeList,
                DiceSimilarity_NodeList,
                OverlapCoefficient_NodeList,
                CosineSimilarity_Without_POS_NodeList,
                DiceSimilarity_Without_POS_NodeList,
                OverlapCoefficient_Without_POS_NodeList;

        String intraLexemeSeparatorCharacter,
                tokenizerChar,
                feature_Separator_Char,
                String1_Id,
                String2_Id;

        int pair_Quality,
                pair_Quality_Predictions_Successfully_Made = 0,
                pair_Quality_Predictions_Failed_Made = 0;

        /*
         * Features File Related
         */
        BufferedWriter bWriter_For_Features_Vector_File = null;
        String line_To_Insert_In_File = null;


        try {
            System.out.println("MonotonicFeaturesSelected File URL is : " + getMonotonic_Features_Selected_File_URL());
            System.out.println("properties.getFeature_Extracted_File_URL() is : " + properties.getFeature_Extracted_File_URL());

            /*
             * set separator character
             */
            tokenizerChar = " ";

            /*
             * set intraLexemeSeparatorCharacter, for 'Tagged String'
             */
            intraLexemeSeparatorCharacter = "/";

            /*
             * Feature Separator Character
             */
            feature_Separator_Char = ",";

            /*
             * Get XML Document, for <code>properties.getFeature_Extracted_File_URL()()
             * 
             */
            doc = XMLProcessor.getXMLDocumentForXMLFile(properties.getFeature_Extracted_File_URL());

            /*
             * 2. Get Document Element
             */
            root = doc.getDocumentElement();


            /*************************************************************
             ****************** Complete Monotonic Set *******************
             *************************************************************
             */

            /*
             * 3. A
             *
             * Complete Monotonic
             * 
             * Create "Features Vector's" file with proper name
             */
            bWriter_For_Features_Vector_File = FileSystemManager.createFile(FileSystemManager.addSuffixToFileURL(getMonotonic_Features_Selected_File_URL(), "_complete", "txt"));

            /*
             * 4. A
             * 
             * Complete Monotonic
             *
             * Select all Required Monotonic 'NodeLists'
             */
            pairs = root.getElementsByTagName("Pair");

//             Select "Real Strings"
            String1_NodeList = root.getElementsByTagName("String1");
            String2_NodeList = root.getElementsByTagName("String2");

//             Event Detection
            CosineSimilarityRaw_NodeList = root.getElementsByTagName("CosineSimilarityRaw");
            
           
            /*
             * 5. loop through all pairs
             *
             * Complete Monotonic
             *
             */
            for (int i = 0; i < pairs.getLength(); i++) {
                /**********************************************
                 *********** Features Selection ***************
                 **********************************************
                 */
                /*
                 * Extract "Pair"
                 */
                pair = pairs.item(i);

                /*
                 * Extract Pair Quality
                 */
                pair_Quality = Integer.parseInt(pair.getAttributes().getNamedItem("Quality").getNodeValue());

                /*
                 * Extract String Ids
                 */
                String1_Id = String1_NodeList.item(i).getAttributes().getNamedItem("Id").getNodeValue();
                String2_Id = String2_NodeList.item(i).getAttributes().getNamedItem("Id").getNodeValue();

                /*
                 * Extract CosineSimilarityRaw value
                 */
                CosineSimilarityRaw_String = CosineSimilarityRaw_NodeList.item(i).getTextContent();


                /**********************************************
                 ******** Feature Vector Formulation **********
                 **********************************************
                 */
                /*
                 * Heading String
                 */
                if (i == 0) {
                    /*
                     * Add heading String with "Feature Names"
                     *
                     * LcsOfCharactersBasedCosineSimilarity,LcsOfLexemesBasedCosineSimilarity,LcsBasedOnTokensBasedCosineSimilarity,LcsOfCharactersBasedOverlapCoefficient,LcsOfLexemesBasedOverlapCoefficient,LcsBasedOnTokensBasedOverlapCoefficient
                     *
                     * String1Id,String2Id,CosineSimilarityRaw,pair_Quality
                     */
                    line_To_Insert_In_File = "String1Id,"
                            + "String2Id,"
                            + "CosineSimilarityRaw,"
                            + "pair_Quality";

                    /*
                     * TEST
                     */
                    System.out.println("\nFeatures : \t" + line_To_Insert_In_File + "\n");


                    /*
                     * Insert Line
                     *
                     * and, new Line
                     */
                    bWriter_For_Features_Vector_File.write(line_To_Insert_In_File);
                    bWriter_For_Features_Vector_File.newLine();
                }

                /*
                 * Real Feature Vector
                 */
                line_To_Insert_In_File = null;

                /*
                 * String Ids
                 */
                line_To_Insert_In_File = appendString(line_To_Insert_In_File, String1_Id, feature_Separator_Char);
                line_To_Insert_In_File = appendString(line_To_Insert_In_File, String2_Id, feature_Separator_Char);

                /*
                 * Cosine Similarity ... CosineSimilarityRaw
                 */
                line_To_Insert_In_File = appendString(line_To_Insert_In_File, CosineSimilarityRaw_String, feature_Separator_Char);
                                
                /*
                 * Pair Quality
                 */
                line_To_Insert_In_File = appendString(line_To_Insert_In_File, Integer.toString(pair_Quality), null);

                /**********************************************
                 ******* Feature Vector Writing  **************
                 **********************************************
                 */
                bWriter_For_Features_Vector_File.write(line_To_Insert_In_File);
                bWriter_For_Features_Vector_File.newLine();

                /*
                 * TEST
                 */
                System.out.println(line_To_Insert_In_File);

                System.out.println((i + 1) + " / " + pairs.getLength() + "\n");
            }

            /*
             * 6.  Flush Bufferred Writer
             */
            bWriter_For_Features_Vector_File.flush();

            /*************************************************************
             ************* Ends Complete Monotonic Set *******************
             *************************************************************
             */

            /*
             * 6. Flush and Close Writer             
             */
            bWriter_For_Features_Vector_File.flush();
            bWriter_For_Features_Vector_File.close();

        } catch (Exception e) {
            throw new Exception("MonotonicFeatureSelector : Process :"
                    + e + " : " + e.getMessage());
        }
    }

    /*
     * Helper Methods
     */
    private String getMonotonic_Features_Selected_File_URL() {
        return monotonic_Features_Selected_File_URL;
    }

    private void setMonotonic_Features_Selected_File_URL(Properties properties) throws Exception {
        /*
         * Local Declarations
         */
        String suffixToAdd,
                newURL;
        try {
            /*
             * add Suffix, "_features_selected" for "Features Selected"
             */
            suffixToAdd = "_features_selected";

            /*
             * call FileSystemManager.addSuffixToFileURL
             */
            newURL = FileSystemManager.addSuffixToFileURL(properties.getFeature_Extracted_File_URL(), suffixToAdd, "txt");

            /*
             * set instance state
             */
            monotonic_Features_Selected_File_URL = newURL;

            /*
             * set "properties" instance field value
             */
            properties.setMonotonic_Features_Selected_File_URL(newURL);

        } catch (Exception e) {
            throw new Exception("MonotonicFeatureSelector : setMonotonic_Features_Selected_File_URL : "
                    + e + " : " + e.getMessage());
        }
    }

    /*
     * This is to be used for feature vector construction task
     */
    private String appendString(String string_To_Be_Appended, String string_To_Append, String separator_Char) throws Exception {
        /*
         * Local Declarations
         */
        try {
            /*
             * Append String
             */

            if (string_To_Be_Appended == null || string_To_Be_Appended.equalsIgnoreCase("")) {
                /*
                 * If its new
                 */
                string_To_Be_Appended = string_To_Append;
            } else {
                /*
                 * If its used
                 */
                string_To_Be_Appended += string_To_Append;
            }

            /*
             * Append Separator
             */
            if (separator_Char != null) {
                string_To_Be_Appended += separator_Char;
            }

        } catch (Exception e) {
            throw new Exception("MonotonicFeatureSelector : appendString : "
                    + e + " : " + e.getMessage());
        }

        return string_To_Be_Appended;
    }
}
