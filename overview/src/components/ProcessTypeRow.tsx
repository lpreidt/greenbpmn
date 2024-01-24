import { Box, Typography } from "@mui/material";
import { Stack } from "@mui/system";
import { ProcessCard } from "./ProcessCard";
import ArrowIcon from "@mui/icons-material/ChevronRight";
import { Process } from "../types/Process";

type ProcessTypeRowProps = {
  title: string;
  processes: Process[];
};

export default function ProcessTypeRow(props: ProcessTypeRowProps) {
  const processCards = props.processes.map((process: any, index) => {
    const isLast = index === props.processes.length - 1;

    return (
      <>
        <ProcessCard process={process} />
        {isLast ? (
          <></>
        ) : (
          <Box marginX={2}>
            <ArrowIcon />
          </Box>
        )}
      </>
    );
  });

  return (
    <>
      <Stack direction="row" alignItems="center" >
        <Box >
          <Typography variant="h6" fontWeight='bold' marginRight={10} minWidth={200} 
          >
            
            
            {props.title}
            
          </Typography>
        </Box>
        <Stack direction="row" alignItems="center">
          {processCards}
        </Stack>
      </Stack>
    </>
  );
}
