package models;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PostmanWorkspaceAddModel {

    public Map<String, String> getWorkspace() {
        return workspace;
    }

    public void setWorkspace(Map<String, String> workspace) {
        this.workspace = workspace;
    }

    private Map<String, String> workspace;


}
