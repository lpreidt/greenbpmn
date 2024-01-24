import { Stack } from "@mui/material";
import NavBar from "../components/Navbar";
import { useProcessStore } from "../stores/process_store";
import ProcessLandscape from "../components/ProcessLandscape";
import { useParams } from "react-router-dom";
import { MuiSnackbar } from "../components/MuiSnackbar";

export default function ProcessDetails() {
  const { id } = useParams();

  const getProcess = useProcessStore((state) => state.getProcess);
  const getParentProcess = useProcessStore((state) => state.getParentProcess);
  const getChildProcesses = useProcessStore((state) => state.getChildProcesses);

  const process = id ? getProcess(id) : undefined;
  const name = process?.name || 'Unbekannt';

  const parentProcess = id ? getParentProcess(id) : undefined;
  const coreProcesses = id ? getChildProcesses(id, 'core') : [];
  const managementProcesses = id ? getChildProcesses(id, 'management') : [];
  const supportProcesses = id ? getChildProcesses(id, 'support') : [];

  const hasChildProcess = coreProcesses.length > 0 || managementProcesses.length > 0 || supportProcesses.length > 0;

  return (

    <div>
      <Stack>
        <NavBar
          title={"Prozess: " + name}
        />
        <ProcessLandscape
          parentProcess={parentProcess}
          coreProcesses={coreProcesses}
          managementProcesses={managementProcesses}
          supportProcesses={supportProcesses}
        />
      </Stack>
      <MuiSnackbar open={!hasChildProcess} text='Dieser Prozess hat keine weiteren Teilprozesse!' />
    </div>
  );
}
