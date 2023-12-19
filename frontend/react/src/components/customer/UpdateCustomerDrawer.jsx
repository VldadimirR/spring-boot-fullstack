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
import CreateCustomerForm from "../shared/CreateCustomerForm.jsx";
import UpdateCustomerForm from "./UpdateCustomerForm.jsx";

const AddIcon = () => "+";
const CloseIcon = () => "x";

const UpdateCustomerDrawer = ({ fetchCustomers,  initialValues, customerId}) => {
    const { isOpen, onOpen, onClose } = useDisclosure()
    return <>
        <Button
            bg={'gray.200'}
            color={'black'}
            rounder={'full'}
            _hover={{
                transform: 'translateY(-2px)',
                boxShadow: 'lg'
            }}
            onClick={onOpen}
        >
            Update customer
        </Button>
        <Drawer isOpen={isOpen} onClose={onClose} >
            <DrawerOverlay />
            <DrawerContent>
                <DrawerCloseButton />
                <DrawerHeader>Update customer</DrawerHeader>

                <DrawerBody>
                    <UpdateCustomerForm
                        fetchCustomers={fetchCustomers}
                        initialValues={initialValues}
                        customerId={customerId}
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

export default UpdateCustomerDrawer
