import { Card, Typography, CardContent, Box, Stack } from "@mui/material";
import { useNavigate } from "react-router-dom";
import { Process } from "../types/Process";

type ProcessCardProps = {
  process: Process;
};

function colorBox(energy: number): string {
  if (energy <= 30) {
    return "#33CC00"; /* green */
  } else if (energy > 30 && energy <= 170) {
    return "#FFEF00"; /* yellow */
  } else if (energy > 170) {
    return "#FF3300"; /* red */
  }
  return "gray";
}

export const ProcessCard = (props: ProcessCardProps) => {
  let navigate = useNavigate();

  var energySumYear = props.process.energySumYear;
  var newBackground: string = colorBox(energySumYear);

  return (
    <Card
      onClick={() => {
        navigate("/details/" + props.process.id);
      }}
      sx={{
       /* maxWidth: 700,
        minWidth: 300,*/
        width:400,
        height: 150,
        margin: "auto",
        cursor: "pointer",
        padding: 0,
        "&:hover": {
          boxShadow: "0 16px 70px -12.125px rgba(0,0,0,0.3)",
        },
      }}
    >
      <CardContent
        sx={{
          padding: 0,
          "&:last-child": {
            paddingBottom: 0,
          },
        }}
      >
        <Stack direction="row" justifyContent="space-between">
          <Typography variant="h6" fontWeight="bold" padding={3}>
          <div style={{ fontSize: '90%' }}>
            {props.process.name}
          </div>
                      
          </Typography>
          <Box
            sx={{
              width: "25%",
              heigth:"150",
              textAlign: "center",
              display: "flex",
              justifyContent: "center" /* align horizontal */,
              alignItems: "center" /* align vertical */,
              fontWeight: "bold",
            }}
            bgcolor={colorBox(energySumYear)}
          >
            <Typography variant="h6" fontWeight="bold">
              {energySumYear}
            </Typography>
          </Box>
        </Stack>
      </CardContent>
    </Card>
  );
};
