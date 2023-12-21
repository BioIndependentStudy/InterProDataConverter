package org.example;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.DBModel.*;
import org.example.DBModel.Model.Site;
import org.example.service.FieldPara;
import org.example.service.JsonUtil;

import java.io.File;
import java.lang.invoke.WrongMethodTypeException;
import java.util.Iterator;
import java.util.Map;
import java.util.*;
import java.io.FileOutputStream;


public class Main {
    public static XSSFWorkbook interproWorkbook = new XSSFWorkbook();
    public static Sheet sheet = interproWorkbook.createSheet("Protein");
    public static int rowNum = 1;
    public static int columnNum = 0;
    public static HashMap<String, Integer> propertyPosition = new HashMap<>();

    public static void main(String[] args) {
        propertyPosition.put("sequence", 1);
        propertyPosition.put("md5", 1);
        propertyPosition.put("signature", 2);
        propertyPosition.put("accession", 3);
        propertyPosition.put("signatureLibraryRelease", 3);
        propertyPosition.put("entry", 3);
        propertyPosition.put("goXrefs", 4);
        propertyPosition.put("pathwayXRefs", 4);
        propertyPosition.put("locations", 2);
        propertyPosition.put("orgType", 2);
        propertyPosition.put("model-ac", 2);
        propertyPosition.put("site", 2);
        propertyPosition.put("siteLocations", 3);
        propertyPosition.put("location-fragments", 2);
        propertyPosition.put("xrefs", 1);

        ObjectMapper mapper = new ObjectMapper();
        try {
            initialWorkbook();
            FieldPara fieldPra = new FieldPara();

            System.out.println("current directory is : " + System.getProperty("user.dir"));
            JsonNode rootNode = mapper.readTree(new File("Lb351-381.json"));
            Map<String, String> flattenedJsonMap = new LinkedHashMap<>();
            //flattenJson(rootNode, "", flattenedJsonMap);
            ProteinHit hit = new ProteinHit();
            JsonUtil.readJson(rootNode, "", flattenedJsonMap, hit, fieldPra, new Stack<String>());
            // printItems(rootNode, "", flattenedJsonMap, hit, parentFieldKey, currentFieldKey);
            //writeToExcel(flattenedJsonMap, "result.xlsx");


            printProteinHit(hit);

            writeWorkbookToExcel(interproWorkbook, "result.xlsx");

        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }


    }

    public static void initialWorkbook() {
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 4000);
        Row header = sheet.createRow(0);

        CellStyle headerStyle = interproWorkbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) interproWorkbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

//        Cell headerCell = header.createCell(0);
//        headerCell.setCellValue("Name");
//        headerCell.setCellStyle(headerStyle);
//
//        headerCell = header.createCell(1);
//        headerCell.setCellValue("Age");
//        headerCell.setCellStyle(headerStyle);


        CellStyle style = interproWorkbook.createCellStyle();
        style.setWrapText(true);
//        Row row = sheet.createRow(1);
//        Cell cell = row.createCell(0);
//        cell.setCellValue("John Smith");
//        cell.setCellStyle(style);
//
//        cell = row.createCell(1);
//        cell.setCellValue(20);
//        cell.setCellStyle(style);
    }

    public static void printProteinHit(ProteinHit hit) {
        System.out.println(hit.getApplications());
        System.out.println(hit.getInterproscanVersion());
        Row row = sheet.createRow(0);

        for (Result result : hit.getResults()) {
//            columnNum = 0;

            System.out.println(" - " + "[Sequence] : " + result.getSequence());
            columnNum = propertyPosition.get("sequence").intValue();
            Cell cell = row.createCell(columnNum);
            cell.setCellValue("Sequence:");
            cell = row.createCell(columnNum + 1);
            cell.setCellValue(result.getSequence());

            System.out.println(" - " + "[MD5] : " + result.getMd5());
            columnNum = propertyPosition.get("md5").intValue();
            row = sheet.createRow(++rowNum);
            cell = row.createCell(columnNum);
            cell.setCellValue("MD5:");
            cell = row.createCell(columnNum + 1);
            cell.setCellValue(result.getMd5());

            System.out.println();
            for (Match match : result.getMatches()) {
                Signature signature = match.getSignature();
                row = sheet.createRow(++rowNum);
                columnNum = propertyPosition.get("signature").intValue();
                cell = row.createCell(columnNum);
                cell.setCellValue("<Signature>");
                System.out.println(" - - " + "[Accession] : " + signature.getAccession());
                row = sheet.createRow(++rowNum);
                cell = row.createCell(++columnNum);
                cell.setCellValue("Accession:");
                cell = row.createCell(columnNum + 1);
                cell.setCellValue(signature.getAccession());
                System.out.println(" - - " + "[Name] : " + signature.getName());
                row = sheet.createRow(++rowNum);
                cell = row.createCell(columnNum);
                cell.setCellValue("Name:");
                cell = row.createCell(columnNum + 1);
                cell.setCellValue(signature.getName());
                System.out.println(" - - " + "[Description] : " + signature.getDescription());
                row = sheet.createRow(++rowNum);
                cell = row.createCell(columnNum);
                cell.setCellValue("Description:");
                cell = row.createCell(columnNum + 1);
                cell.setCellValue(signature.getDescription());
                SignatureLibraryRelease signatureLibraryRelease = signature.getSignatureLibraryRelease();
                row = sheet.createRow(++rowNum);
                columnNum = propertyPosition.get("signatureLibraryRelease").intValue();
                cell = row.createCell(columnNum);
                cell.setCellValue("<SignatureLibraryRelease>");
                System.out.println(" - - - " + "[Library] : " + signatureLibraryRelease.getLibrary());
                row = sheet.createRow(++rowNum);
                cell = row.createCell(columnNum + 1);
                cell.setCellValue("Library:");
                cell = row.createCell(columnNum + 2);
                cell.setCellValue(signatureLibraryRelease.getLibrary());
                System.out.println(" - - - " + "[Version] : " + signatureLibraryRelease.getVersion());
                row = sheet.createRow(++rowNum);
                cell = row.createCell(columnNum + 1);
                cell.setCellValue("Version:");
                cell = row.createCell(columnNum + 2);
                cell.setCellValue(signatureLibraryRelease.getVersion());
                Entry entry = signature.getEntry();
                row = sheet.createRow(++rowNum);
                columnNum = propertyPosition.get("entry").intValue();
                cell = row.createCell(columnNum);
                cell.setCellValue("<entry>");
                System.out.println(" - - - - " + "[Accession] : " + entry.getAccession());
                row = sheet.createRow(++rowNum);
                cell = row.createCell(columnNum + 1);
                cell.setCellValue("accession:");
                cell = row.createCell(columnNum + 2);
                cell.setCellValue(entry.getAccession());
                System.out.println(" - - - - " + "[Name] : " + entry.getName());
                row = sheet.createRow(++rowNum);
                cell = row.createCell(columnNum + 1);
                cell.setCellValue("Name:");
                cell = row.createCell(columnNum + 2);
                cell.setCellValue(entry.getName());
                System.out.println(" - - - - " + "[Description] : " + entry.getDescription());
                row = sheet.createRow(++rowNum);
                cell = row.createCell(columnNum + 1);
                cell.setCellValue("Description:");
                cell = row.createCell(columnNum + 2);
                cell.setCellValue(entry.getDescription());
                System.out.println(" - - - - " + "[Type] : " + entry.getType());
                row = sheet.createRow(++rowNum);
                cell = row.createCell(columnNum + 1);
                cell.setCellValue("Type:");
                cell = row.createCell(columnNum + 2);
                cell.setCellValue(entry.getType());
                LinkedList<GoXRefs> goXRefses = entry.getGoXRefses();
                System.out.println("");
                for (GoXRefs ref : goXRefses) {
                    ;
                    ;
                    ;
                }
                LinkedList<PathwayXRefs> pathwayXRefses = entry.getPathwayXRefses();
                for (PathwayXRefs ref : pathwayXRefses) {
                    row = sheet.createRow(++rowNum);
                    columnNum = propertyPosition.get("pathwayXRefs").intValue();
                    cell = row.createCell(columnNum);
                    cell.setCellValue("[PathwayXRefs]");
                    System.out.println(" - - - - - " + "[Name] : " + ref.getName());
                    row = sheet.createRow(++rowNum);
                    cell = row.createCell(columnNum + 1);
                    cell.setCellValue("[name]");
                    cell = row.createCell(columnNum + 2);
                    cell.setCellValue(ref.getName());
                    System.out.println(" - - - - - " + "[Database Name] : " + ref.getDatabaseName());
                    row = sheet.createRow(++rowNum);
                    cell = row.createCell(columnNum + 1);
                    cell.setCellValue("DatabaseName:");
                    cell = row.createCell(columnNum + 2);
                    cell.setCellValue(ref.getDatabaseName());
                    System.out.println(" - - - - - " + "[Id] : " + ref.getId());
                    row = sheet.createRow(++rowNum);
                    cell = row.createCell(columnNum + 1);
                    cell.setCellValue("Id:");
                    cell = row.createCell(columnNum + 2);
                    cell.setCellValue(ref.getId());
                    System.out.println("");
                }
                LinkedList<Location> locations = match.getLocations();
                columnNum = propertyPosition.get("locations");
                row = sheet.createRow(++rowNum);
                cell = row.createCell(columnNum);
                cell.setCellValue("[Locations]");
                for (Location location : locations) {
                    System.out.println(" - - - - - - " + "[Start] : " + location.getStart());
                    row = sheet.createRow(++rowNum);
                    cell = row.createCell(columnNum + 1);
                    cell.setCellValue("Start:");
                    cell = row.createCell(columnNum + 2);
                    cell.setCellValue(location.getStart());
                    System.out.println(" - - - - - - " + "[End] : " + location.getEnd());
                    row = sheet.createRow(++rowNum);
                    cell = row.createCell(columnNum + 1);
                    cell.setCellValue("End:");
                    cell = row.createCell(columnNum + 2);
                    cell.setCellValue(location.getEnd());
                    System.out.println(" - - - - - - " + "[HmmStart] : " + location.getHmmStart());
                    row = sheet.createRow(++rowNum);
                    cell = row.createCell(columnNum + 1);
                    cell.setCellValue("HmmStart:");
                    cell = row.createCell(columnNum + 2);
                    cell.setCellValue(location.getHmmStart());
                    System.out.println(" - - - - - - " + "[HmmEnd] : " + location.getHmmEnd());
                    row = sheet.createRow(++rowNum);
                    cell = row.createCell(columnNum + 1);
                    cell.setCellValue("HmmEnd:");
                    cell = row.createCell(columnNum + 2);
                    cell.setCellValue(location.getHmmEnd());
                    System.out.println(" - - - - - - " + "[HmmLength] : " + location.getHmmLength());
                    row = sheet.createRow(++rowNum);
                    cell = row.createCell(columnNum + 1);
                    cell.setCellValue("HmmLength:");
                    cell = row.createCell(columnNum + 2);
                    cell.setCellValue(location.getHmmLength());
                    System.out.println(" - - - - - - " + "[HmmBounds] : " + location.getHmmBounds());
                    row = sheet.createRow(++rowNum);
                    cell = row.createCell(columnNum + 1);
                    cell.setCellValue("HmmBounds:");
                    cell = row.createCell(columnNum + 2);
                    cell.setCellValue(location.getHmmBounds());
                    System.out.println(" - - - - - - " + "[E Value] : " + location.geteValue());
                    row = sheet.createRow(++rowNum);
                    cell = row.createCell(columnNum + 1);
                    cell.setCellValue("E Value:");
                    cell = row.createCell(columnNum + 2);
                    cell.setCellValue(location.geteValue());
                    System.out.println(" - - - - - - " + "[EnvelopStart] : " + location.getEnvelopeStart());
                    row = sheet.createRow(++rowNum);
                    cell = row.createCell(columnNum + 1);
                    cell.setCellValue("EnvelopStart:");
                    cell = row.createCell(columnNum + 2);
                    cell.setCellValue(location.getEnvelopeStart());
                    System.out.println(" - - - - - - " + "[EnvelopeEnd] : " + location.getEnvelopeEnd());
                    row = sheet.createRow(++rowNum);
                    cell = row.createCell(columnNum + 1);
                    cell.setCellValue("EnvelopEnd:");
                    cell = row.createCell(columnNum + 2);
                    cell.setCellValue(location.getEnvelopeEnd());
                    System.out.println(" - - - - - - " + "[Post Processed] : " + location.isPostProcessed());
                    row = sheet.createRow(++rowNum);
                    cell = row.createCell(columnNum + 1);
                    cell.setCellValue("Postprocessed:");
                    cell = row.createCell(columnNum + 2);
                    cell.setCellValue(location.isPostProcessed());
                    System.out.println(" - - - - - - " + "[Score] : " + location.getScore());
                    row = sheet.createRow(++rowNum);
                    cell = row.createCell(columnNum + 1);
                    cell.setCellValue("Score:");
                    cell = row.createCell(columnNum + 2);
                    cell.setCellValue(location.getScore());

                    LinkedList<Site> sites = location.getSites();
                    columnNum = propertyPosition.get("sites");
                    row = sheet.createRow(++rowNum);
                    cell = row.createCell(columnNum);
                    cell.setCellValue("[Sites]");
                    for (Site site : sites) {
                        System.out.println(" - - - - - - - " + "[Description] : " + site.getDescription());
                        row = sheet.createRow(++rowNum);
                        cell = row.createCell(columnNum + 1);
                        cell.setCellValue("Description:");
                        cell = row.createCell(columnNum + 2);
                        cell.setCellValue(site.getDescription());
                        System.out.println(" - - - - - - - " + "[NumLocations] : " + site.getNumLocations());
                        row = sheet.createRow(++rowNum);
                        cell = row.createCell(columnNum + 1);
                        cell.setCellValue("NumLocations:");
                        cell = row.createCell(columnNum + 2);
                        cell.setCellValue(site.getNumLocations());
                        System.out.println("");
                        LinkedList<SiteLocation> siteLocations = site.getSiteLocations();
                        columnNum = propertyPosition.get("siteLocations");
                        row = sheet.createRow(++rowNum);
                        cell = row.createCell(columnNum);
                        cell.setCellValue("[Site Locations]");
                        for (SiteLocation siteLocation : siteLocations) {
                            System.out.println(" - - - - - - - - " + "[Start] : " + siteLocation.getStart());
                            row = sheet.createRow(++rowNum);
                            cell = row.createCell(columnNum + 1);
                            cell.setCellValue("Start:");
                            cell = row.createCell(columnNum + 2);
                            cell.setCellValue(siteLocation.getStart());
                            System.out.println(" - - - - - - - - " + "[End] : " + siteLocation.getEnd());
                            row = sheet.createRow(++rowNum);
                            cell = row.createCell(columnNum + 1);
                            cell.setCellValue("End:");
                            cell = row.createCell(columnNum + 2);
                            cell.setCellValue(siteLocation.getEnd());
                            System.out.println(" - - - - - - - - " + "[Residue] : " + siteLocation.getResidue());
                            row = sheet.createRow(++rowNum);
                            cell = row.createCell(columnNum + 1);
                            cell.setCellValue("Residue:");
                            cell = row.createCell(columnNum + 2);
                            cell.setCellValue(siteLocation.getResidue());
                            System.out.println("");
                        }
                    }
                    LinkedList<LocationFragment> locationFragments = location.getLocationFragments();
                    columnNum = propertyPosition.get("location-fragments");
                    row = sheet.createRow(++rowNum);
                    cell = row.createCell(columnNum);
                    cell.setCellValue("[Location Fragments]");
                    for (LocationFragment locationFragment : locationFragments) {
                        System.out.printf(" - - - - - - - " + "[Start] : " + String.valueOf(locationFragment.getStart()));
                        row = sheet.createRow(++rowNum);
                        cell = row.createCell(columnNum + 1);
                        cell.setCellValue("Start:");
                        cell = row.createCell(columnNum + 2);
                        cell.setCellValue(locationFragment.getStart());
                        System.out.println(" - - - - - - - " + "[End] : " + String.valueOf(locationFragment.getEnd()));
                        row = sheet.createRow(++rowNum);
                        cell = row.createCell(columnNum + 1);
                        cell.setCellValue("End:");
                        cell = row.createCell(columnNum + 2);
                        cell.setCellValue(locationFragment.getEnd());
                        System.out.println(" - - - - - - - " + "[dc Status] : " + locationFragment.getDcStatus());
                        row = sheet.createRow(++rowNum);
                        cell = row.createCell(columnNum + 1);
                        cell.setCellValue("DcStatus:");
                        cell = row.createCell(columnNum + 2);
                        cell.setCellValue(locationFragment.getDcStatus());
                        System.out.println("");
                    }

                }
                System.out.println("[orgType] : " + match.getOrgType());
                columnNum = propertyPosition.get("orgType").intValue();
                row = sheet.createRow(++rowNum);
                cell = row.createCell(columnNum);
                cell.setCellValue("OrgType:");
                cell = row.createCell(columnNum + 1);
                cell.setCellValue(match.getOrgType());
                System.out.println("[Model Ac] : " + match.getModelAc());
                columnNum = propertyPosition.get("model-ac").intValue();
                row = sheet.createRow(++rowNum);
                cell = row.createCell(columnNum);
                cell.setCellValue("ModelAc:");
                cell = row.createCell(columnNum + 1);
                cell.setCellValue(match.getModelAc());
            }
            LinkedList<Xref> xrefs = result.getXrefs();
            columnNum = propertyPosition.get("xrefs");
            row = sheet.createRow(++rowNum);
            cell = row.createCell(columnNum);
            cell.setCellValue("[Xrefs]");
            for (Xref xref : xrefs) {
                System.out.println(" - - " + "[Name] : " + xref.getName());
                row = sheet.createRow(++rowNum);
                cell = row.createCell(columnNum + 1);
                cell.setCellValue("Name:");
                cell = row.createCell(columnNum + 2);
                cell.setCellValue(xref.getName());
                System.out.println(" - - " + "[Id] : " + xref.getId());
                row = sheet.createRow(++rowNum);
                cell = row.createCell(columnNum + 1);
                cell.setCellValue("Id:");
                cell = row.createCell(columnNum + 2);
                cell.setCellValue(xref.getId());
                System.out.println("");
            }
        }
    }

    //    public static void BackupMethod_printProteinHit(ProteinHit hit) {
//        System.out.println(hit.getApplications());
//        System.out.println(hit.getInterproscanVersion());
//        for (Result result : hit.getResults()) {
//            System.out.println(result.getSequence());
//            System.out.println(result.getMd5());
//            for (Match match : result.getMatches()) {
//                Signature signature = match.getSignature();
//                System.out.println(signature.getAccession());
//                System.out.println(signature.getName());
//                System.out.println(signature.getDescription());
//                SignatureLibraryRelease signatureLibraryRelease = signature.getSignatureLibraryRelease();
//                System.out.println(signatureLibraryRelease.getLibrary());
//                System.out.println(signatureLibraryRelease.getVersion());
//                Entry entry = signature.getEntry();
//                System.out.println(entry.getAccession());
//                System.out.println(entry.getName());
//                System.out.println(entry.getDescription());
//                System.out.println(entry.getType());
//                LinkedList<GoXRefs> goXRefses = entry.getGoXRefses();
//                for (GoXRefs ref : goXRefses) {
//                    ;
//                }
//                LinkedList<PathwayXRefs> pathwayXRefses = entry.getPathwayXRefses();
//                for (PathwayXRefs ref : pathwayXRefses) {
//                    System.out.println(ref.getName());
//                    System.out.println(ref.getDatabaseName());
//                    System.out.println(ref.getId());
//                }
//                LinkedList<Location> locations = match.getLocations();
//                for (Location location : locations) {
//                    System.out.println(location.getStart());
//                    System.out.println(location.getEnd());
//                    System.out.println(location.getHmmStart());
//                    System.out.println(location.getHmmEnd());
//                    System.out.println(location.getHmmLength());
//                    System.out.println(location.getHmmBounds());
//                    System.out.println(location.getEvalue());
//                    System.out.println(location.getEnvelopeStart());
//                    System.out.println(location.getEnvolopeEnd());
//                    System.out.println(location.isPostProcessed());
//                    System.out.println(location.getScore());
//                    LinkedList<Site> sites = location.getSites();
//                    for (Site site : sites) {
//                        System.out.println(site.getDescription());
//                        System.out.println(site.getNumLocations());
//                        LinkedList<SiteLocation> siteLocations = site.getSiteLocations();
//                        for (SiteLocation siteLocation : siteLocations) {
//                            System.out.println(siteLocation.getStart());
//                            System.out.println(siteLocation.getEnd());
//                            System.out.println(siteLocation.getResidue());
//                        }
//                    }
//                    LinkedList<LocationFragment> locationFragments = location.getLocationFragments();
//                    for (LocationFragment locationFragment : locationFragments) {
//                        System.out.printf(String.valueOf(locationFragment.getStart()));
//                        System.out.println(String.valueOf(locationFragment.getEnd()));
//                        System.out.println(locationFragment.getDcStatus());
//                    }
//                    System.out.println(match.getOrgType());
//                    System.out.println(match.getModelAc());
//                }
//                LinkedList<Xref> xrefs = result.getXrefs();
//                for (Xref xref : xrefs) {
//                    System.out.println(xref.getName());
//                    System.out.println(xref.getId());
//                }
//            }
//
//        }
//    }
//
    private static void printItems_Backup(JsonNode jsonNode, String parentKey, Map<String, String> flattenedJsonMap) {
        if (jsonNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            int i = 0;
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();

                String newKey = parentKey.isEmpty() ? field.getKey() : parentKey + "." + field.getKey();

                System.out.println(field.getValue() + "    " + newKey + "     ");
                printItems_Backup(field.getValue(), newKey, flattenedJsonMap);

            }
        } else {
            flattenedJsonMap.put(parentKey, jsonNode.asText());
            System.out.println("parent key is <" + parentKey.toString() + "> and node text is : " + jsonNode.asText());
        }
    }

    private static void flattenJson(JsonNode jsonNode, String
            parentKey, Map<String, String> flattenedJsonMap) {
        if (jsonNode.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                String newKey = parentKey.isEmpty() ? field.getKey() : parentKey + "." + field.getKey();
                flattenJson(field.getValue(), newKey, flattenedJsonMap);
            }
        } else {
            flattenedJsonMap.put(parentKey, jsonNode.asText());
            System.out.println("parent key is <" + parentKey.toString() + "> and node text is : " + jsonNode.asText());
        }
    }

    private static void writeToExcel(Map<String, String> data, String filePath) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Flattened JSON");

        int rowNum = 0;
        Row headerRow = sheet.createRow(rowNum++);

        // Optional
        headerRow.createCell(0).setCellValue("Key");
        headerRow.createCell(1).setCellValue("Value");

        for (Map.Entry<String, String> entry : data.entrySet()) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(entry.getKey());
            row.createCell(1).setCellValue(entry.getValue());
        }

        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
        }
        workbook.close();
    }

//    private static Workbook addToWorkbook(Workbook workbook) {
//
//    }

    private static void writeWorkbookToExcel(XSSFWorkbook workbook, String filePath) {
        try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}