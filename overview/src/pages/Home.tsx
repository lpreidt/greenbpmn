import { Stack } from "@mui/material";
import { MuiSnackbar } from "../components/MuiSnackbar";
import NavBar from "../components/Navbar";
import ProcessLandscape from "../components/ProcessLandscape";
import { useProcessStore } from "../stores/process_store";

export default function Home() {
  const allProcesses = useProcessStore((state) => state.allProcesses);
  const fetchProcesses = useProcessStore((state) => state.fetchProcesses);
  const coreProcesses = useProcessStore((state) => state.coreProcesses);
  const managementProcess = useProcessStore(
    (state) => state.managementProcesses
  );
  const supportProcess = useProcessStore((state) => state.supportProcesses);

  if (allProcesses.length === 0) {
    fetchProcesses();
  }

  const getToplevelProcesesFromProcesses = useProcessStore(
    (state) => state.getToplevelProcesesFromProcesses
  );

  const topLevelCoreProcesses = getToplevelProcesesFromProcesses(coreProcesses);
  const topLevelManagementProcesses = getToplevelProcesesFromProcesses(managementProcess);
  const topLevelSupportProcesses = getToplevelProcesesFromProcesses(supportProcess);

  return (
    <Stack>
      <NavBar />
      <ProcessLandscape
        coreProcesses={topLevelCoreProcesses}
        managementProcesses={topLevelManagementProcesses}
        supportProcesses={topLevelSupportProcesses}
      />
    </Stack>
  );
}
