import { Grid, Stack, Box } from "@mui/material";
import NavBar from "../components/Navbar";
import { useProcessStore } from "../stores/process_store";
import ProcessLandscape from "../components/ProcessLandscape";
import { useParams } from "react-router-dom";
import { ProcessCard } from "../components/ProcessCard";
import SubHeader from "../components/Subheader";

export default function ProcessEnergySort() {
  const { energyLevel } = useParams();

  const allProcesses = useProcessStore((state) => state.allProcesses);

  var filteredProcesses: any = [];

  if (energyLevel === "low") {
    filteredProcesses = allProcesses.filter((obj: any) => {
      return obj.energySumYear <= 30;
    });
  }
  if (energyLevel === "medium") {
    filteredProcesses = allProcesses.filter((obj: any) => {
      return obj.energySumYear > 30 && obj.energySumYear <= 170;
    });
  }
  if (energyLevel === "high") {
    filteredProcesses = allProcesses.filter((obj: any) => {
      return obj.energySumYear > 170;
    });
  }

  filteredProcesses.sort((a: any, b: any) =>
    a.energySumYear < b.energySumYear ? 1 : -1
  );

  const processCards = filteredProcesses.map((process: any) => {
    return (
      <Grid item>
        <ProcessCard process={process} />
      </Grid>
    );
  });

  return (
    <div>
      <Stack>
        <NavBar />
        <SubHeader />
        <Box padding={5}>
          <Grid container spacing={2}>
            {processCards}
          </Grid>
        </Box>
      </Stack>
    </div>
  );
}
