package org.example.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import org.example.DBModel.*;
import org.example.DBModel.Model.Site;

import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class JsonUtil {

    public static void readJson(JsonNode jsonNode, String parentKey, Map<String, String> flattenedJsonMap, ProteinHit hit, FieldPara para, Stack<String> parentFieldKeyStack) {
        if (para.parentFieldKey.isEmpty()) {
            para.parentFieldKey = "hit";
            parentFieldKeyStack.push("hit");
        }
        if (jsonNode.isObject()) {
            System.out.println("<OBJECT>");
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNode.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                System.out.println("Field Key is : " + field.getKey());
                para.currentFieldKey = field.getKey();
                System.out.println("Set Current Key as : " + field.getKey());
                System.out.println("Current Field Key is : " + para.currentFieldKey);
                String newKey = parentKey.isEmpty() ? field.getKey() : parentKey + "." + field.getKey();
                System.out.println("New Full Key is : " + newKey);
                System.out.println("-----------------------------------------------------------------------------");

                boolean isParentKeyUpdated = criteriaCheck(para, para.currentFieldKey, field.getValue(), hit, parentFieldKeyStack);

                readJson(field.getValue(), newKey, flattenedJsonMap, hit, para, parentFieldKeyStack);

                if (isParentKeyUpdated) {
                    parentFieldKeyStack.pop();
//                    para.parentFieldKey = parentFieldKeyStack.peek();
                }
            }
        } else if (jsonNode.getNodeType() == JsonNodeType.ARRAY) {
            System.out.println("<ARRAY>");
            for (JsonNode jsonArrayNode : jsonNode) {
                Iterator<Map.Entry<String, JsonNode>> fields = jsonArrayNode.fields();
                while (fields.hasNext()) {

                    Map.Entry<String, JsonNode> field = fields.next();
                    para.currentFieldKey = field.getKey();

                    System.out.println("Set Current Key as : " + field.getKey());
                    System.out.println("Current Field Key is : " + para.currentFieldKey);
                    String newKey = parentKey.isEmpty() ? field.getKey() : parentKey + "." + field.getKey();
                    System.out.println("New Full Key is : " + newKey);
                    System.out.println("-----------------------------------------------------------------------------");

                    boolean isParentKeyUpdated = criteriaCheck(para, para.currentFieldKey, field.getValue(), hit, parentFieldKeyStack);
                    readJson(field.getValue(), newKey, flattenedJsonMap, hit, para, parentFieldKeyStack);
                    if (isParentKeyUpdated) {
                        parentFieldKeyStack.pop();
                        para.parentFieldKey = parentFieldKeyStack.peek();
                    }
                }
            }
        } else {
            flattenedJsonMap.put(parentKey, jsonNode.asText());
        }
    }

    public static boolean criteriaCheck(FieldPara para, String currentFieldKey, JsonNode currentNode, ProteinHit hit, Stack<String> parentKeyFieldStack) {

        boolean[] isParentkeyUpdated = new boolean[1]; // Wrapper class
        isParentkeyUpdated[0] = false;

        if (para.parentFieldKey == "hit") {
            if (currentFieldKey == "application") {
                hit.setApplications(currentNode.asText());
            } else if (currentFieldKey == "interproscan-version") {
                hit.setInterproscanVersion(currentNode.asText());
            } else if (currentFieldKey == "results") {
                setParentFieldKey(para, "results", parentKeyFieldStack, isParentkeyUpdated);
                System.out.println("parent field key is set to result here");
            }

        } else if (para.parentFieldKey == "results") {
            if (currentFieldKey == "sequence") {
                Result result = new Result();
                result.setSequence(currentNode.asText());
                hit.getResults().add(result);
            } else if (currentFieldKey == "md5") {
                hit.getResults().getLast().setMd5(currentNode.asText());
            } else if (currentFieldKey == "matches") {
                setParentFieldKey(para, "matches", parentKeyFieldStack, isParentkeyUpdated);
            } else if (currentFieldKey == "xref") {
                setParentFieldKey(para, "xref", parentKeyFieldStack, isParentkeyUpdated);
            }
        } else if (para.parentFieldKey == "matches") {
            if (currentFieldKey == "signature") {
                Match match = new Match();
                hit.getResults().getLast().getMatches().add(match);
                setParentFieldKey(para, "signature", parentKeyFieldStack, isParentkeyUpdated);
            } else if (currentFieldKey == hit.getApplications()) {
                setParentFieldKey(para, "locations", parentKeyFieldStack, isParentkeyUpdated);
            } else if (currentFieldKey == "orgType") {
                hit.getResults().getLast().getMatches().getLast().setOrgType(currentNode.asText());
            } else if (currentFieldKey == "model-ac") {
                hit.getResults().getLast().getMatches().getLast().setModelAc(currentNode.asText());
            }
        } else if (para.parentFieldKey == "signature") {
            if (currentFieldKey == "accession") {
                hit.getResults().getLast().getMatches().getLast().getSignature().setAccession(currentNode.asText());
            } else if (currentFieldKey == "name") {
                hit.getResults().getLast().getMatches().getLast().getSignature().setName(currentNode.asText());
            } else if (currentFieldKey == "description") {
                hit.getResults().getLast().getMatches().getLast().getSignature().setDescription(currentNode.asText());
            } else if (currentFieldKey == "signatureLibraryRelease") {
                setParentFieldKey(para, "signatureLibraryRelease", parentKeyFieldStack, isParentkeyUpdated);
            } else if (currentFieldKey == "entry") {
                setParentFieldKey(para, "entry", parentKeyFieldStack, isParentkeyUpdated);
                //hit.getResults().getLast().getMatches().getLast().getSignature().setEntry(currentNode.asText());
            }
        } else if (para.parentFieldKey == "signatureLibraryRelease") {
            if (currentFieldKey == "library") {
                hit.getResults().getLast().getMatches().getLast().getSignature().getSignatureLibraryRelease().setLibrary(currentNode.asText());
            } else if (currentFieldKey == "version") {
                hit.getResults().getLast().getMatches().getLast().getSignature().getSignatureLibraryRelease().setVersion(currentNode.asText());
                setParentFieldKey(para, "signature", parentKeyFieldStack, isParentkeyUpdated);
            }
        } else if (para.parentFieldKey == "locations") {
            if (currentFieldKey == "start") {
                Location location = new Location();
                location.setStart(currentNode.asInt());
                hit.getResults().getLast().getMatches().getLast().getLocations().add(location);
            } else if (currentFieldKey == "end") {
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().setEnd(currentNode.asInt());
            } else if (currentFieldKey == "location-fragments") {
                setParentFieldKey(para, "location-fragments", parentKeyFieldStack, isParentkeyUpdated);
            } else if (currentFieldKey == "sequence-feature") {
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().setSequenceFeature(currentNode.asText());
            } else if (currentFieldKey == "score") {
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().setScore(currentNode.asDouble());
            } else if (currentFieldKey == "hmmStart") {
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().setHmmStart(currentNode.asInt());
            } else if (currentFieldKey == "hmmEnd") {
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().setHmmEnd(currentNode.asInt());
            } else if (currentFieldKey == "hmmLength") {
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().setHmmLength(currentNode.asInt());
            } else if (currentFieldKey == "hmmBounds") {
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().setHmmBounds(currentNode.asText());
            } else if (currentFieldKey == "evalue") {
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().seteValue(currentNode.asDouble());
            } else if (currentFieldKey == "envelopeStart") {
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().setEnvelopeStart(currentNode.asInt());
            } else if (currentFieldKey == "envelopeEnd") {
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().setEnvelopeEnd(currentNode.asInt());
            } else if (currentFieldKey == "postProcessed") {
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().setPostProcessed(currentNode.asBoolean());
            } else if (currentFieldKey == "sites") {
                setParentFieldKey(para, "sites", parentKeyFieldStack, isParentkeyUpdated);
            }

        } else if (para.parentFieldKey == "location-fragments") {
            if (currentFieldKey == "start") {
                LocationFragment fragment = new LocationFragment();
                fragment.setStart(currentNode.asInt());
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().getLocationFragments().add(fragment);
            } else if (currentFieldKey == "end") {
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().getLocationFragments().getLast().setEnd(currentNode.asInt());
            } else if (currentFieldKey == "dc-status") {
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().getLocationFragments().getLast().setDcStatus(currentNode.asText());
            }
        } else if (para.parentFieldKey == "xref") {
            if (currentFieldKey == "name") {
                Xref xref = new Xref();
                xref.setName(currentNode.asText());
                hit.getResults().getLast().getXrefs().add(xref);
            } else if (currentFieldKey == "id") {
                hit.getResults().getLast().getXrefs().getLast().setId(currentNode.asText());
            }
        } else if (para.parentFieldKey == "sites") {
            if (currentFieldKey == "description") {
                Site site = new Site();
                site.setDescription(currentNode.asText());
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().getSites().add(site);
            } else if (currentFieldKey == "numLocation") {
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().getSites().getLast().setNumLocations(currentNode.asInt());
            } else if (currentFieldKey == "siteLocations") {
                setParentFieldKey(para, "siteLocations", parentKeyFieldStack, isParentkeyUpdated);
            }
        } else if (para.parentFieldKey == "siteLocations") {
            if (currentFieldKey == "start") {
                SiteLocation siteLocation = new SiteLocation();
                siteLocation.setStart(currentNode.asInt());
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().getSites().getLast().getSiteLocations().add(siteLocation);
            } else if (currentFieldKey == "end") {
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().getSites().getLast().getSiteLocations().getLast().setEnd(currentNode.asInt());

            } else if (currentFieldKey == "residue") {
                hit.getResults().getLast().getMatches().getLast().getLocations().getLast().getSites().getLast().getSiteLocations().getLast().setResidue(currentNode.asText());

            }
        } else if (para.parentFieldKey == "entry") {
            if (currentFieldKey == "accession") {
                hit.getResults().getLast().getMatches().getLast().getSignature().getEntry().setAccession(currentNode.asText());
            } else if (currentFieldKey == "name") {
                hit.getResults().getLast().getMatches().getLast().getSignature().getEntry().setName(currentNode.asText());
            } else if (currentFieldKey == "description") {
                hit.getResults().getLast().getMatches().getLast().getSignature().getEntry().setDescription(currentNode.asText());
            } else if (currentFieldKey == "type") {
                hit.getResults().getLast().getMatches().getLast().getSignature().getEntry().setType(currentNode.asText());
            } else if (currentFieldKey == "goXrefs") {
                setParentFieldKey(para, "goXrefs", parentKeyFieldStack, isParentkeyUpdated);
            } else if (currentFieldKey == "pathwayXRefs") {
                setParentFieldKey(para, "pathwayXRefs", parentKeyFieldStack, isParentkeyUpdated);
            }
        } else if (para.parentFieldKey == "pathwayXRefs") {
            if (currentFieldKey == "name") {
                PathwayXRefs refs = new PathwayXRefs();
                refs.setName(currentNode.asText());
                hit.getResults().getLast().getMatches().getLast().getSignature().getEntry().getPathwayXRefses().add(refs);
            } else if (currentFieldKey == "databaseName") {
                hit.getResults().getLast().getMatches().getLast().getSignature().getEntry().getPathwayXRefses().getLast().setDatabaseName(currentNode.asText());
            } else if (currentFieldKey == "id") {
                hit.getResults().getLast().getMatches().getLast().getSignature().getEntry().getPathwayXRefses().getLast().setId(currentNode.asText());
            }
        }
        return isParentkeyUpdated[0];
    }

    private static void setParentFieldKey(FieldPara para, String
            newParentFieldKey, Stack<String> parentFieldKeyStack, boolean[] isParentKeyUpdated) {
        para.parentFieldKey = newParentFieldKey;
        parentFieldKeyStack.push(newParentFieldKey);
        isParentKeyUpdated[0] = true;
    }

}
