package repo.dtos;

public class OverviewDto {

    private String id;

    private String name;

    private String childProcess;

    private String parentProcess;

    private String startKnoten;

    private String endKnoten;

    private int energySumYear;

    private String processType;

    private String department;

    private String processDescription;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChildProcess() {
        return childProcess;
    }

    public void setChildProcess(String childProcess) {
        this.childProcess = childProcess;
    }

    public String getStartKnoten() {
        return startKnoten;
    }

    public void setStartKnoten(String startKnoten) {
        this.startKnoten = startKnoten;
    }

    public String getEndKnoten() {
        return endKnoten;
    }

    public void setEndKnoten(String endKnoten) {
        this.endKnoten = endKnoten;
    }

    public int getEnergySumYear() {
        return energySumYear;
    }

    public void setEnergySumYear(int energySumYear) {
        this.energySumYear = energySumYear;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getProcessDescription() {
        return processDescription;
    }

    public void setProcessDescription(String processDescription) {
        this.processDescription = processDescription;
    }

    public void setParentProcess(String parentProcess) { this.parentProcess = parentProcess;
    }
    public String getParentProcess() {
        return parentProcess;
    }
}
