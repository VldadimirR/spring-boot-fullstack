import React from 'react'
import {ComponentPreview, Previews} from '@react-buddy/ide-toolbox'
import {PaletteTree} from './palette'
import CreateCustomerForm from "../components/CreateCustomerForm.jsx";

const ComponentPreviews = () => {
    return (
        <Previews palette={<PaletteTree/>}>
            <ComponentPreview path="/CreateCustomerForm">
                <CreateCustomerForm/>
            </ComponentPreview>
        </Previews>
    )
}

export default ComponentPreviews