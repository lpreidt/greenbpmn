import LogoutIcon from "@mui/icons-material/Logout";
import { IconButton, Tooltip } from "@mui/material";
import AppBar from "@mui/material/AppBar";
import Box from "@mui/material/Box";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import { useNavigate } from "react-router-dom";
import logo from "../images/logo.svg";

type NavBarProps = {
  title?: string;
}

export default function NavBar(props: NavBarProps) {
  const navigate = useNavigate();

  const title = props.title == null ? "Übersicht" : props.title;

  return (
    <>
      <Box
        sx={{
          flexGrow: 1,
          top: 0,
          position: "fixed",
          width: "100%",
          zIndex: 500,
        }}
      >
        <AppBar
          position="static"
          style={{ background: "white", color: "black", padding: 0 }}
        >
          <Toolbar>
            <Box
              marginRight={2}
              component="img"
              sx={{
                height: 80,
                cursor: "pointer",
              }}
              alt="Logo"
              src={logo}
              onClick={() => {
                navigate("/");
              }}
            />

            <Typography
              variant="h6"
              component="div"
              sx={{ flexGrow: 1, fontWeight: "bold" }}
            >
              {title}
            </Typography>
          </Toolbar>
        </AppBar>
      </Box>
      <Box height={80} />
    </>
  );
}
