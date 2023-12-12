import { Button,
         Drawer,
         DrawerBody,
         DrawerFooter,
         DrawerHeader,
         DrawerOverlay,
         DrawerContent,
         DrawerCloseButton,
         useDisclosure,
         Input
} from "@chakra-ui/react"
import CreateCustomerForm from "./CreateCustomerForm.jsx";

const AddIcon = () => "+";
const CloseIcon = () => "x";

const CreateCustomerDrawer = ({ fetchCustomers }) => {
    const { isOpen, onOpen, onClose } = useDisclosure()
    return <>
        <Button
            leftIcon={<AddIcon/>}
            colorScheme={"teal"}
            onClick={onOpen}
        >
            Create customer
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} >
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                <DrawerHeader>Create new Customer</DrawerHeader>

                <DrawerBody>
                    <CreateCustomerForm
                    fetchCustomers={fetchCustomers}
                    />
                </DrawerBody>

                <DrawerFooter>
                    <Button
                        leftIcon={<CloseIcon/>}
                        colorScheme={"teal"}
                        onClick={onClose}
                    >
                        Close
                    </Button>
                </DrawerFooter>
            </DrawerContent>
        </Drawer>
            </>
}

export default CreateCustomerDrawer

