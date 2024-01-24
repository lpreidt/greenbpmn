import { Snackbar, Button, Alert } from '@mui/material'
import {useEffect, useState } from 'react'


type SnackbarProps = {
    open: boolean;
    text: string;
};

export const MuiSnackbar = (props: SnackbarProps) => {
    const  [open, setOpen] = useState(false)
    const handleClose = (
        event?: React.SyntheticEvent | Event,
        reason?: string
    ) => {
            if (reason === 'clickaway') {
            return
        }

        setOpen(false)
    }


    useEffect(() => {
        setOpen(props.open)
     }, [props.open])


     return (
        <>
        <Snackbar 
        autoHideDuration={3000}
        open={open}
        onClose={handleClose}
        anchorOrigin={{
            vertical: 'bottom',
            horizontal: 'center',
        }}>
            <Alert onClose={handleClose} severity="info" sx={{
                width: '100%' }}>
                {props.text}
            </Alert>
            </Snackbar>

            


        </>

     )
}
        


