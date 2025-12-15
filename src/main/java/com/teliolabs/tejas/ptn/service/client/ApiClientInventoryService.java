package com.teliolabs.tejas.ptn.service.client;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teliolabs.tejas.ptn.config.ApplicationConfig;
import com.teliolabs.tejas.ptn.config.Endpoint;
import com.teliolabs.tejas.ptn.config.NetworkManagerConfig;
import com.teliolabs.tejas.ptn.context.ApplicationContext;
import com.teliolabs.tejas.ptn.dto.inventory.TopologyOltService;
import com.teliolabs.tejas.ptn.dto.inventory.TopologyService;
import com.teliolabs.tejas.ptn.dto.inventory.TrailService;
import com.teliolabs.tejas.ptn.dto.inventory.TunnelService;
import com.teliolabs.tejas.ptn.repository.TopologyRepo;
import com.teliolabs.tejas.ptn.repository.TrailRepo;
import com.teliolabs.tejas.ptn.repository.TunnelRepo;
import com.teliolabs.tejas.ptn.util.AdditionalInformation;
import com.teliolabs.tejas.ptn.util.ConnectionEndPoint;
import com.teliolabs.tejas.ptn.util.ConnectivityService;
import com.teliolabs.tejas.ptn.util.EndPoint;
import com.teliolabs.tejas.ptn.util.EndpointConstants;
import com.teliolabs.tejas.ptn.util.Name;
import com.teliolabs.tejas.ptn.util.NodeEdgePoint;
import com.teliolabs.tejas.ptn.util.Ont;
import com.teliolabs.tejas.ptn.util.Root;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiClientInventoryService extends BaseApiClientService {

    private final ObjectMapper objectMapper;
    private final ApiClientAuthService apiClientAuthService;
    private final TopologyService topologyService;
    private final TunnelService tunnelService;
    private final TrailService trailService;
    private final TopologyRepo topologyRepo;
    private final TunnelRepo tunnelRepo;
    private final TrailRepo trailRepo;

    @Autowired
    public ApiClientInventoryService(ApplicationContext applicationContext, WebClient.Builder webClientBuilder,
            ApplicationConfig applicationConfig, ObjectMapper objectMapper, TopologyService topologyService,
            TunnelService tunnelService,
            TopologyOltService topologyOltService, TrailService trailService, ApiClientAuthService apiClientAuthService,
            TopologyRepo topologyRepo, TunnelRepo tunnelRepo, TrailRepo trailRepo) {
        super(applicationContext, webClientBuilder, applicationConfig);
        this.objectMapper = objectMapper;
        this.topologyService = topologyService;
        this.tunnelService = tunnelService;
        this.trailService = trailService;
        this.apiClientAuthService = apiClientAuthService;
        this.topologyRepo = topologyRepo;
        this.trailRepo = trailRepo;
        this.tunnelRepo = tunnelRepo;
    }

    // Service method with token refresh logic
    public List<TopologyNodeDetail> getPtn1PdDetails() {
        // Get Network Manager Config
        List<Root> nodeLists = getPtn1NodeList();
        List<TopologyNodeDetail> pdDetailsList = new ArrayList<>();

        TopologyNodeDetail nodesList = null;
        for (Root nodeList : nodeLists) {
            String uuid = nodeList.getUuid();
            NetworkManagerConfig networkManager = applicationConfig.getNetworkManager();
            // Fetch the correct endpoint for getting node list
            Endpoint endpoint = networkManager.getEndpoints().stream()
                    .filter(e -> e.getName().equals(EndpointConstants.GET_NODE_DETAILS))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Endpoint not found"));

            // Build the WebClient and make the request
            nodesList = webClientBuilder
                    .baseUrl(getEndpointHost(endpoint))
                    .build()
                    .method(resolveMethod(endpoint))
                    .uri(uriBuilder -> uriBuilder.path(getEndpointPath(endpoint))
                            .build(uuid))
                    .headers(headers -> headers.setBearerAuth(applicationContext.getAuthContext().getAccessToken()))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<TopologyNodeDetail>() {
                    })
                    .block(); // Blocking call, consider using async if possible
            if (nodesList != null) {
                pdDetailsList.add(nodesList);
            }

        }

        return pdDetailsList;
    }

    public List<TopologyNodeDetail> getPtn2PdDetails() {
        // Get Network Manager Config
        List<Root> nodeLists = getPtn2NodeList();
        List<TopologyNodeDetail> pdDetailsList = new ArrayList<>();

        TopologyNodeDetail nodesList = null;
        for (Root nodeList : nodeLists) {
            String uuid = nodeList.getUuid();
            NetworkManagerConfig networkManager = applicationConfig.getNetworkManager();
            // Fetch the correct endpoint for getting node list
            Endpoint endpoint = networkManager.getEndpoints().stream()
                    .filter(e -> e.getName().equals(EndpointConstants.GET_NODE2_DETAILS))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Endpoint not found"));

            // Build the WebClient and make the request
            nodesList = webClientBuilder
                    .baseUrl(getEndpointHost(endpoint))
                    .build()
                    .method(resolveMethod(endpoint))
                    .uri(uriBuilder -> uriBuilder.path(getEndpointPath(endpoint))
                            .build(uuid))
                    .headers(headers -> headers.setBearerAuth(applicationContext.getAuthContext().getAccessToken()))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<TopologyNodeDetail>() {
                    })
                    .block(); // Blocking call, consider using async if possible
            if (nodesList != null) {
                pdDetailsList.add(nodesList);
            }

        }

        return pdDetailsList;
    }

    public List<Root> getPtn1NodeList() {
        // Get Network Manager Config
        NetworkManagerConfig networkManager = applicationConfig.getNetworkManager();

        // Fetch the correct endpoint for getting node list
        Endpoint endpoint = networkManager.getEndpoints().stream()
                .filter(e -> e.getName().equals(EndpointConstants.GET_NODE_LIST))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Endpoint not found"));

        // Build the WebClient and make the request
        List<Root> nodeList = webClientBuilder
                .baseUrl(getEndpointHost(endpoint))
                .build()
                .method(resolveMethod(endpoint))
                .uri(uriBuilder -> uriBuilder.path(getEndpointPath(endpoint))
                        .build("TTLEMS-PTN-1"))
                .headers(headers -> headers.setBearerAuth(applicationContext.getAuthContext().getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Root>>() {
                })
                .block(); // Blocking call, consider using async if possible
        // // Log the response
        // log.info("nodes: {}", nodeList);

        return nodeList;
    }

    public List<Root> getPtn2NodeList() {
        // Get Network Manager Config
        NetworkManagerConfig networkManager = applicationConfig.getNetworkManager();

        // Fetch the correct endpoint for getting node list
        Endpoint endpoint = networkManager.getEndpoints().stream()
                .filter(e -> e.getName().equals(EndpointConstants.GET_NODE_LIST))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Endpoint not found"));

        // Build the WebClient and make the request
        List<Root> nodeList = webClientBuilder
                .baseUrl(getEndpointHost(endpoint))
                .build()
                .method(resolveMethod(endpoint))
                .uri(uriBuilder -> uriBuilder.path(getEndpointPath(endpoint))
                        .build("TTLEMS-PTN-2"))
                .headers(headers -> headers.setBearerAuth(applicationContext.getAuthContext().getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Root>>() {
                })
                .block(); // Blocking call, consider using async if possible
        // // Log the response
        // log.info("nodes: {}", nodeList);

        return nodeList;
    }

    public List<Root> getPtn1LinkList() {
        // Get Network Manager Config
        NetworkManagerConfig networkManager = applicationConfig.getNetworkManager();

        // Fetch the correct endpoint for getting node list
        Endpoint endpoint = networkManager.getEndpoints().stream()
                .filter(e -> e.getName().equals(EndpointConstants.GET_PTN_1_LINK_LIST))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Endpoint not found"));

        // Build the WebClient and make the request
        List<Root> nodeList = webClientBuilder
                .baseUrl(getEndpointHost(endpoint))
                .build()
                .method(resolveMethod(endpoint))
                .uri(uriBuilder -> uriBuilder.path(getEndpointPath(endpoint))
                        .build("TTLEMS-PTN-1"))
                .headers(headers -> headers.setBearerAuth(applicationContext.getAuthContext().getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Root>>() {
                })
                .block(); // Blocking call, consider using async if possible
        // // Log the response
        // log.info("nodes: {}", nodeList);

        return nodeList;
    }

    public List<TopologyNodeDetail> getPtn1LinkDetails() {
        // Get Network Manager Config
        List<Root> getLinkList = getPtn1LinkList();
        List<TopologyNodeDetail> topologyDetails = new ArrayList<>();

        TopologyNodeDetail linksList = null;
        for (Root linkList : getLinkList) {
            String linkUuid = linkList.getUuid();
            NetworkManagerConfig networkManager = applicationConfig.getNetworkManager();
            // Fetch the correct endpoint for getting node list
            Endpoint endpoint = networkManager.getEndpoints().stream()
                    .filter(e -> e.getName().equals(EndpointConstants.GET_PTN_1_LINK_DETAILS))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Endpoint not found"));

            // Build the WebClient and make the request
            linksList = webClientBuilder
                    .baseUrl(getEndpointHost(endpoint))
                    .build()
                    .method(resolveMethod(endpoint))
                    .uri(uriBuilder -> uriBuilder.path(getEndpointPath(endpoint))
                            .build(linkUuid))
                    .headers(headers -> headers.setBearerAuth(applicationContext.getAuthContext().getAccessToken()))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<TopologyNodeDetail>() {
                    })
                    .block(); // Blocking call, consider using async if possible
            if (linksList != null) {
                topologyDetails.add(linksList);
            }

        }

        return topologyDetails;
    }

    public List<Root> getPtn2LinkList() {
        // Get Network Manager Config
        NetworkManagerConfig networkManager = applicationConfig.getNetworkManager();

        // Fetch the correct endpoint for getting node list
        Endpoint endpoint = networkManager.getEndpoints().stream()
                .filter(e -> e.getName().equals(EndpointConstants.GET_PTN_2_LINK_LIST))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Endpoint not found"));

        // Build the WebClient and make the request
        List<Root> nodeList = webClientBuilder
                .baseUrl(getEndpointHost(endpoint))
                .build()
                .method(resolveMethod(endpoint))
                .uri(uriBuilder -> uriBuilder.path(getEndpointPath(endpoint))
                        .build("TTLEMS-PTN-2"))
                .headers(headers -> headers.setBearerAuth(applicationContext.getAuthContext().getAccessToken()))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Root>>() {
                })
                .block(); // Blocking call, consider using async if possible
        // // Log the response
        // log.info("nodes: {}", nodeList);

        return nodeList;
    }

    public List<TopologyNodeDetail> getPtn2LinkDetails() {
        // Get Network Manager Config
        List<Root> getLinkList = getPtn2LinkList();
        List<TopologyNodeDetail> topologyDetails = new ArrayList<>();

        TopologyNodeDetail linksList = null;
        for (Root linkList : getLinkList) {
            String linkUuid = linkList.getUuid();
            NetworkManagerConfig networkManager = applicationConfig.getNetworkManager();
            // Fetch the correct endpoint for getting node list
            Endpoint endpoint = networkManager.getEndpoints().stream()
                    .filter(e -> e.getName().equals(EndpointConstants.GET_PTN_2_LINK_DETAILS))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Endpoint not found"));

            // Build the WebClient and make the request
            linksList = webClientBuilder
                    .baseUrl(getEndpointHost(endpoint))
                    .build()
                    .method(resolveMethod(endpoint))
                    .uri(uriBuilder -> uriBuilder.path(getEndpointPath(endpoint))
                            .build(linkUuid))
                    .headers(headers -> headers.setBearerAuth(applicationContext.getAuthContext().getAccessToken()))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<TopologyNodeDetail>() {
                    })
                    .block(); // Blocking call, consider using async if possible
            if (linksList != null) {
                topologyDetails.add(linksList);
            }

        }

        return topologyDetails;
    }

    public void getPTN1TopologyData() {
        List<String[]> topologyData = new ArrayList<>();
        List<String[]> tunnelData = new ArrayList<>();
        List<TopologyNodeDetail> getLinkDetailList = getPtn1LinkDetails();
        List<TopologyNodeDetail> getNodeNames = getPtn1PdDetails();

        for (TopologyNodeDetail getLinkDetails : getLinkDetailList) {
            String trailId = "null", userLabel = "null", circuitId = "null", rate = "null";
            String aEndDropPort = "null", zEndDropPort = "null", topology = "null";
            String aEndDropNode = "null", zEndDropNode = "null", channel = "null";
            String aEndNode = "null", zEndNode = "null", aEndPort = "null";
            String aEndNodeObj = "null", zEndNodeObj = "null";
            String zEndPort = "null", topologyType = "null", circle = "null";
            String uuid = "null", ZEndCapacity = "null";
            ArrayList<AdditionalInformation> additionalInformations = getLinkDetails.getAdditionalIinformation();

            for (AdditionalInformation topologyaddinfo : additionalInformations) {
                if (topologyaddinfo.valueName.equals("layer-rate")) {
                    String rateCode = topologyaddinfo.value;
                    if (rateCode.contains("19")) {
                        rate = "STM-0";
                    } else if (rateCode.equals("19")) {
                        rate = "STM0";
                    } else if (rateCode.equals("73") || rateCode.equals("25") || rateCode.equals("20")
                            || rateCode.equals("93")) {
                        rate = "STM1";
                    } else if (rateCode.equals("74") || rateCode.equals("21") || rateCode.equals("26")) {
                        rate = "STM4";
                    } else if (rateCode.equals("75") || rateCode.equals("89") || rateCode.equals("88")) {
                        rate = "STM8";
                    } else if (rateCode.equals("76") || rateCode.equals("22") || rateCode.equals("27")) {
                        rate = "STM16";
                    } else if (rateCode.equals("77") || rateCode.equals("28") || rateCode.equals("23")) {
                        rate = "STM64";
                    } else if (rateCode.equals("78") || rateCode.equals("91") || rateCode.equals("90")) {
                        rate = "STM256";
                    } else if (rateCode.equals("96")) {
                        rate = "10GigE";
                    }
                } else if (topologyaddinfo.valueName.equals("ZEndCapacity")) {
                    ZEndCapacity = calculateRate(topologyaddinfo.value);
                } else if (topologyaddinfo.valueName.equals("user-label")) {
                    userLabel = topologyaddinfo.value;
                } else if (topologyaddinfo.valueName.equals("src-tp-label")) {
                    aEndPort = topologyaddinfo.value;
                } else if (topologyaddinfo.valueName.equals("dest-tp-label")) {
                    zEndPort = topologyaddinfo.value;
                }

                if (ZEndCapacity.equals("1 GigE")) {
                    rate = "1GigE";
                }

            }

            String nativeEmsName = getLinkDetails.getUuid();
            ArrayList<NodeEdgePoint> nodeEdgePoints = getLinkDetails.getNodeEdgePoint();
            aEndNodeObj = nodeEdgePoints.get(0).getNodeUuid();
            zEndNodeObj = nodeEdgePoints.get(1).getNodeUuid();
            for (TopologyNodeDetail getNodeName : getNodeNames) {
                if (getNodeName.getUuid().equals(aEndNodeObj)) {
                    ArrayList<AdditionalInformation> nodeAdditionalInformations = getNodeName
                            .getAdditionalIinformation();
                    for (AdditionalInformation nodeAdditionalInformation : nodeAdditionalInformations) {
                        if (nodeAdditionalInformation.valueName.equals("nativeEMSName")) {
                            aEndNode = nodeAdditionalInformation.value;
                        }
                    }
                } else if (getNodeName.getUuid().equals(zEndNodeObj)) {
                    ArrayList<AdditionalInformation> nodeAdditionalInformations = getNodeName
                            .getAdditionalIinformation();
                    for (AdditionalInformation nodeAdditionalInformation : nodeAdditionalInformations) {
                        if (nodeAdditionalInformation.valueName.equals("nativeEMSName")) {
                            zEndNode = nodeAdditionalInformation.value;
                        }
                    }
                }
            }

            circle = aEndNode.split("_")[0];

            // Set default values if necessary
            LocalDateTime currentDateTime = LocalDateTime.now();
            String lastModified = currentDateTime.toString();
            String topologyUserLabel = String.format("%s-%s-%s-%s", aEndNode, aEndPort, zEndNode, zEndPort);
            String Technology = "";
            String Specification = "";
            if (aEndPort.contains("STM") || zEndPort.contains("STM")) {
                Technology = "SDH";
                Specification = "SDH_TOPOLOGICAL_LINK_CON";
            } else {
                Technology = "Ethernet";
                Specification = "INNI Connectivity";
            }
            // Collect data for topology
            String[] row = { userLabel, rate, Technology, Specification, "Tejas PTN-1", "Tejas PTN-1",
                    "Tejas PTN-1", aEndNode,
                    zEndNode, aEndPort, zEndPort, circle, nativeEmsName, lastModified };
            topologyData.add(row);

            // Collect data for tunnel
            String[] row2 = { trailId, userLabel, circuitId, rate, Technology, Specification, "MAIN",
                    "Tejas PTN-1",
                    topologyUserLabel,
                    aEndDropNode, zEndDropNode, aEndDropPort, zEndDropPort, aEndNode, zEndNode, aEndPort, zEndPort,
                    circle, "NE2NE", lastModified };
            tunnelData.add(row2);
        }

        // Save data and write to CSV
        // tunnelService.saveTunnelData(tunnelData);
        topologyRepo.truncateTable();
        topologyService.saveTopologyData(topologyData);
    }

    private String calculateRate(String value) {

        if (value.equals("1000")) {
            return "1 GigE";
        } else if (value.equals("10000")) {
            return "10 GigE";
        }
        return "1 GigE";
    }

    public void getPTN2TopologyData() {
        List<String[]> topologyData = new ArrayList<>();
        List<String[]> tunnelData = new ArrayList<>();
        List<TopologyNodeDetail> getLinkDetailList = getPtn2LinkDetails();
        List<TopologyNodeDetail> getNodeNames = getPtn2PdDetails();

        for (TopologyNodeDetail getLinkDetails : getLinkDetailList) {
            String trailId = "null", userLabel = "null", circuitId = "null", rate = "null";
            String aEndDropPort = "null", zEndDropPort = "null", topology = "null";
            String aEndDropNode = "null", zEndDropNode = "null", channel = "null";
            String aEndNode = "null", zEndNode = "null", aEndPort = "null";
            String aEndNodeObj = "null", zEndNodeObj = "null";
            String zEndPort = "null", topologyType = "null", circle = "null";
            String uuid = "null", ZEndCapacity = "null";
            ArrayList<AdditionalInformation> additionalInformations = getLinkDetails.getAdditionalIinformation();

            for (AdditionalInformation topologyaddinfo : additionalInformations) {
                if (topologyaddinfo.valueName.equals("layer-rate")) {
                    String rateCode = topologyaddinfo.value;
                    if (rateCode.contains("19")) {
                        rate = "STM-0";
                    } else if (rateCode.equals("19")) {
                        rate = "STM0";
                    } else if (rateCode.equals("73") || rateCode.equals("25") || rateCode.equals("20")
                            || rateCode.equals("93")) {
                        rate = "STM1";
                    } else if (rateCode.equals("74") || rateCode.equals("21") || rateCode.equals("26")) {
                        rate = "STM4";
                    } else if (rateCode.equals("75") || rateCode.equals("89") || rateCode.equals("88")) {
                        rate = "STM8";
                    } else if (rateCode.equals("76") || rateCode.equals("22") || rateCode.equals("27")) {
                        rate = "STM16";
                    } else if (rateCode.equals("77") || rateCode.equals("28") || rateCode.equals("23")) {
                        rate = "STM64";
                    } else if (rateCode.equals("78") || rateCode.equals("91") || rateCode.equals("90")) {
                        rate = "STM256";
                    } else if (rateCode.equals("96")) {
                        rate = "10GigE";
                    }
                } else if (topologyaddinfo.valueName.equals("ZEndCapacity")) {
                    ZEndCapacity = calculateRate(topologyaddinfo.value);
                } else if (topologyaddinfo.valueName.equals("user-label")) {
                    userLabel = topologyaddinfo.value;
                } else if (topologyaddinfo.valueName.equals("src-tp-label")) {
                    aEndPort = topologyaddinfo.value;
                } else if (topologyaddinfo.valueName.equals("dest-tp-label")) {
                    zEndPort = topologyaddinfo.value;
                }
                if (ZEndCapacity.equals("1 GigE")) {
                    rate = "1GigE";
                }

            }

            String nativeEmsName = getLinkDetails.getUuid();
            ArrayList<NodeEdgePoint> nodeEdgePoints = getLinkDetails.getNodeEdgePoint();
            aEndNodeObj = nodeEdgePoints.get(0).getNodeUuid();
            zEndNodeObj = nodeEdgePoints.get(1).getNodeUuid();
            for (TopologyNodeDetail getNodeName : getNodeNames) {
                if (getNodeName.getUuid().equals(aEndNodeObj)) {
                    ArrayList<AdditionalInformation> nodeAdditionalInformations = getNodeName
                            .getAdditionalIinformation();
                    for (AdditionalInformation nodeAdditionalInformation : nodeAdditionalInformations) {
                        if (nodeAdditionalInformation.valueName.equals("nativeEMSName")) {
                            aEndNode = nodeAdditionalInformation.value;
                        }
                    }
                } else if (getNodeName.getUuid().equals(zEndNodeObj)) {
                    ArrayList<AdditionalInformation> nodeAdditionalInformations = getNodeName
                            .getAdditionalIinformation();
                    for (AdditionalInformation nodeAdditionalInformation : nodeAdditionalInformations) {
                        if (nodeAdditionalInformation.valueName.equals("nativeEMSName")) {
                            zEndNode = nodeAdditionalInformation.value;
                        }
                    }
                }
            }

            circle = aEndNode.split("_")[0];

            // Set default values if necessary
            LocalDateTime currentDateTime = LocalDateTime.now();
            String lastModified = currentDateTime.toString();
            String topologyUserLabel = String.format("%s-%s-%s-%s", aEndNode, aEndPort, zEndNode, zEndPort);
            String Technology = "";
            String Specification = "";
            if (aEndPort.contains("STM") || zEndPort.contains("STM")) {
                Technology = "SDH";
                Specification = "SDH_TOPOLOGICAL_LINK_CON";
            } else {
                Technology = "Ethernet";
                Specification = "INNI Connectivity";
            }

            // Collect data for topology
            String[] row = { userLabel, rate, Technology, Specification, "Tejas PTN-2", "Tejas PTN-2",
                    "Tejas PTN-2", aEndNode,
                    zEndNode, aEndPort, zEndPort, circle, nativeEmsName, lastModified };
            topologyData.add(row);

            // Collect data for tunnel
            String[] row2 = { trailId, userLabel, circuitId, rate, Technology, Specification, "MAIN",
                    "Tejas PTN-2",
                    topologyUserLabel,
                    aEndDropNode, zEndDropNode, aEndDropPort, zEndDropPort, aEndNode, zEndNode, aEndPort, zEndPort,
                    circle, "NE2NE", lastModified };
            tunnelData.add(row2);
        }

        // Save data and write to CSV
        // tunnelService.saveTunnelData(tunnelData);
        topologyService.saveTopologyData(topologyData);
        // writeCsv(topologyData, tunnelData);
    }

    private List<String> formatValues(String[] values) {
        List<String> formattedValues = new ArrayList<>();
        for (String value : values) {
            // Escape double quotes and commas in the value
            if (value != null) {
                formattedValues.add("\"" + value.replace("\"", "\"\"").replace(",", "") + "\"");
            } else {
                formattedValues.add("");
            }
        }
        return formattedValues;
    }

    public List<Root> getPtn1ServiceDetails() {
        List<Root> nodeLists = getPtn1NodeList();
        List<Root> allServices = new ArrayList<>();

        // Get config and endpoint once
        NetworkManagerConfig networkManager = applicationConfig.getNetworkManager();
        Endpoint endpoint = networkManager.getEndpoints().stream()
                .filter(e -> e.getName().equals(EndpointConstants.GET_SERVICE))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Endpoint not found"));

        for (Root node : nodeLists) {
            String uuid = node.getUuid();

            List<Root> serviceList = webClientBuilder
                    .baseUrl(getEndpointHost(endpoint))
                    .build()
                    .method(resolveMethod(endpoint))
                    .uri(uriBuilder -> uriBuilder
                            .path(getEndpointPath(endpoint))
                            .queryParam("configState", true)
                            .queryParam("csType", "Ethernet")
                            .queryParam("continue", 0)
                            .queryParam("nodeuuid", uuid)
                            .queryParam("size", 500)
                            .build())
                    .headers(headers -> headers.setBearerAuth(applicationContext.getAuthContext().getAccessToken()))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Root>>() {
                    })
                    .block();

            allServices.addAll(serviceList);
        }
        return allServices;
    }

    public List<Root> getPtn2ServiceDetails() {
        List<Root> nodeLists = getPtn2NodeList();
        List<Root> allServices = new ArrayList<>();

        // Get config and endpoint once
        NetworkManagerConfig networkManager = applicationConfig.getNetworkManager();
        Endpoint endpoint = networkManager.getEndpoints().stream()
                .filter(e -> e.getName().equals(EndpointConstants.GET_SERVICE))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Endpoint not found"));

        for (Root node : nodeLists) {
            String uuid = node.getUuid();

            List<Root> serviceList = webClientBuilder
                    .baseUrl(getEndpointHost(endpoint))
                    .build()
                    .method(resolveMethod(endpoint))
                    .uri(uriBuilder -> uriBuilder
                            .path(getEndpointPath(endpoint))
                            .queryParam("configState", true)
                            .queryParam("csType", "Ethernet")
                            .queryParam("continue", 0)
                            .queryParam("nodeuuid", uuid)
                            .queryParam("size", 500)
                            .build())
                    .headers(headers -> headers.setBearerAuth(applicationContext.getAuthContext().getAccessToken()))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<Root>>() {
                    })
                    .block();

            allServices.addAll(serviceList);
        }
        return allServices;
    }

    public void getPtn1ServiceData() {
        List<String[]> tunnelData = new ArrayList<>();

        String trailId = "null", userLabel = "null", circuitId = "null", rate = "null";
        String aEndDropPort = "null", zEndDropPort = "null", topology = "null";
        String aEndDropNode = "null", zEndDropNode = "null", channel = "null";
        String aEndNode = "null", zEndNode = "null", aEndPort = "null";
        String aEndNodeObj = "null", zEndNodeObj = "null";
        String zEndPort = "null", topologyType = "null", circle = "null";
        String uuid = "null";
        String topologyUserLabel = "";
        LocalDateTime currentDateTime = LocalDateTime.now();
        String lastModified = currentDateTime.toString();
        String nodeLabel = "", portLabels = "";

        List<Root> serviceDetails = getPtn1ServiceDetails();
        List<TopologyNodeDetail> getNodeNames = getPtn1PdDetails(); // Move outside loop for efficiency

        for (Root serviceDetail : serviceDetails) {
            if (serviceDetail.getConnectivityService() != null) {
                ConnectivityService connectivityService = serviceDetail.getConnectivityService();
                ArrayList<AdditionalInformation> vlanids = connectivityService.getAdditionalInformation();
                if (connectivityService.getName() != null) {
                    for (AdditionalInformation vlanid : vlanids) {
                        if (vlanid.getValueName().equals("associated-vlans")) {
                            trailId = vlanid.getValue();
                        }
                    }
                    List<Name> serviceNames = connectivityService.getName();
                    for (Name serviceName : serviceNames) {
                        if ("ConnectivityService".equals(serviceName.getValueName())) {
                            userLabel = serviceName.getValue();

                            ArrayList<EndPoint> endPoints = connectivityService.getEndPoint();
                            if (endPoints != null) {
                                for (EndPoint endPoint1 : endPoints) {
                                    if (endPoint1.getConnectionEndPoint() != null) {
                                        for (ConnectionEndPoint connectionEndPoint1 : endPoint1
                                                .getConnectionEndPoint()) {
                                            if (connectionEndPoint1.topologyUuid != null
                                                    && connectionEndPoint1.nodeUuid != null) {
                                                String nodeUuid = connectionEndPoint1.topologyUuid + "|"
                                                        + connectionEndPoint1.nodeUuid;

                                                for (TopologyNodeDetail getNodeName : getNodeNames) {
                                                    if (nodeUuid.equals(getNodeName.getUuid())) {
                                                        ArrayList<AdditionalInformation> nodeAdditionalInformations = getNodeName
                                                                .getAdditionalIinformation();
                                                        if (nodeAdditionalInformations != null) {
                                                            for (AdditionalInformation nodeAdditionalInformation : nodeAdditionalInformations) {
                                                                if ("nativeEMSName"
                                                                        .equals(nodeAdditionalInformation.valueName)) {
                                                                    aEndDropNode = nodeAdditionalInformation.value;
                                                                    // Extract port label from endpoint
                                                                    ArrayList<AdditionalInformation> additionalInformation = endPoint1
                                                                            .getAdditionalInformation();
                                                                    if (additionalInformation != null) {
                                                                        for (AdditionalInformation additionalInformation1 : additionalInformation) {
                                                                            if (additionalInformation1 != null
                                                                                    && "port-label".equals(
                                                                                            additionalInformation1.valueName)) {
                                                                                aEndDropPort = additionalInformation1.value;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }

                            // Extract circuitId and rate
                            circuitId = extractCircuitId(userLabel);
                            rate = extractRate(userLabel);
                            if (rate.contains("UNKNOWN")) {
                                rate = "1 GigE";
                            }

                            // // Debug output
                            // System.out.println("User Label: " + userLabel);
                            // System.out.println("Node Label: " + nodeLabel);
                            // System.out.println("Port Label: " + portLabels);

                            // Construct row
                            String[] row2 = { trailId, userLabel, circuitId, rate, "Ethernet", "INNI Connectivity",
                                    "MAIN",
                                    "PTN-1",
                                    topologyUserLabel,
                                    aEndDropNode, zEndDropNode, aEndDropPort, zEndDropPort, aEndNode, zEndNode,
                                    aEndPort, zEndPort,
                                    circle, "NE2NE", lastModified };
                            tunnelData.add(row2);
                        }
                    }

                }
            }
        }
        trailRepo.truncateTable();
        trailService.saveTrailData(tunnelData);
    }

    public void getPtn2ServiceData() {
        List<String[]> tunnelData = new ArrayList<>();
        String trailId = "null", userLabel = "null", circuitId = "null", rate = "null";
        String aEndDropPort = "null", zEndDropPort = "null", topology = "null";
        String aEndDropNode = "null", zEndDropNode = "null", channel = "null";
        String aEndNode = "null", zEndNode = "null", aEndPort = "null";
        String aEndNodeObj = "null", zEndNodeObj = "null";
        String zEndPort = "null", topologyType = "null", circle = "null";
        String uuid = "null";
        String topologyUserLabel = "";
        LocalDateTime currentDateTime = LocalDateTime.now();
        String lastModified = currentDateTime.toString();
        String nodeLabel = "", portLabels = "";

        List<Root> serviceDetails = getPtn2ServiceDetails();
        List<TopologyNodeDetail> getNodeNames = getPtn2PdDetails(); // Move outside loop for efficiency

        for (Root serviceDetail : serviceDetails) {
            if (serviceDetail.getConnectivityService() != null) {
                ConnectivityService connectivityService = serviceDetail.getConnectivityService();
                ArrayList<AdditionalInformation> vlanids = connectivityService.getAdditionalInformation();
                if (connectivityService.getName() != null) {
                    for (AdditionalInformation vlanid : vlanids) {
                        if (vlanid.getValueName().equals("associated-vlans")) {
                            trailId = vlanid.getValue();
                        }
                    }
                    List<Name> serviceNames = connectivityService.getName();
                    for (Name serviceName : serviceNames) {
                        if ("ConnectivityService".equals(serviceName.getValueName())) {
                            userLabel = serviceName.getValue();

                            ArrayList<EndPoint> endPoints = connectivityService.getEndPoint();
                            if (endPoints != null) {
                                for (EndPoint endPoint1 : endPoints) {
                                    if (endPoint1.getConnectionEndPoint() != null) {
                                        for (ConnectionEndPoint connectionEndPoint1 : endPoint1
                                                .getConnectionEndPoint()) {
                                            if (connectionEndPoint1.topologyUuid != null
                                                    && connectionEndPoint1.nodeUuid != null) {
                                                String nodeUuid = connectionEndPoint1.topologyUuid + "|"
                                                        + connectionEndPoint1.nodeUuid;

                                                for (TopologyNodeDetail getNodeName : getNodeNames) {
                                                    if (nodeUuid.equals(getNodeName.getUuid())) {
                                                        ArrayList<AdditionalInformation> nodeAdditionalInformations = getNodeName
                                                                .getAdditionalIinformation();
                                                        if (nodeAdditionalInformations != null) {
                                                            for (AdditionalInformation nodeAdditionalInformation : nodeAdditionalInformations) {
                                                                if ("nativeEMSName"
                                                                        .equals(nodeAdditionalInformation.valueName)) {
                                                                    aEndDropNode = nodeAdditionalInformation.value;
                                                                    // Extract port label from endpoint
                                                                    ArrayList<AdditionalInformation> additionalInformation = endPoint1
                                                                            .getAdditionalInformation();
                                                                    if (additionalInformation != null) {
                                                                        for (AdditionalInformation additionalInformation1 : additionalInformation) {
                                                                            if (additionalInformation1 != null
                                                                                    && "port-label".equals(
                                                                                            additionalInformation1.valueName)) {
                                                                                aEndDropPort = additionalInformation1.value;
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }

                            // Extract circuitId and rate
                            circuitId = extractCircuitId(userLabel);
                            rate = extractRate(userLabel);
                            if (rate.contains("UNKNOWN")) {
                                rate = "1 GigE";
                            }

                            // // Debug output
                            // System.out.println("User Label: " + userLabel);
                            // System.out.println("Node Label: " + nodeLabel);
                            // System.out.println("Port Label: " + portLabels);

                            // Construct row
                            String[] row2 = { trailId, userLabel, circuitId, rate, "Ethernet", "INNI Connectivity",
                                    "MAIN",
                                    "PTN-2",
                                    topologyUserLabel,
                                    aEndDropNode, zEndDropNode, aEndDropPort, zEndDropPort, aEndNode, zEndNode,
                                    aEndPort, zEndPort,
                                    circle, "NE2NE", lastModified };
                            tunnelData.add(row2);
                        }
                    }
                }
            }
        }
        trailService.saveTrailData(tunnelData);
    }

    public static String extractRate(String input) {
        Pattern pattern = Pattern.compile("\\b\\d{1,5}(M(?:B|BPS|BPLS)?)(?![A-Z])", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group(); // Return the first match
        }
        return "1 GigE"; // No match found
    }

    public static String extractCircuitId(String input) {
        Pattern pattern = Pattern.compile("(\\d{10,13}[A-Z]{0,3})");
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return matcher.group().toUpperCase(); // Return the first match
        }
        return null; // No match found
    }

}