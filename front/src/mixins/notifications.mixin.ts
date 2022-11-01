import { ToastProgrammatic as Toast } from 'buefy'
import { NotificationProgrammatic as Notification } from 'buefy'

const Notifications =  {
    methods: {
        toast(message: string, type: string): void {
            Toast.open({
                duration: 5000,
                message: message,
                type: type,
                pauseOnHover: true,
                position: 'is-top',
            })
        },
        notification(message: string, type: string): void {
            Notification.open({
                duration: 5000,
                message: message,
                position: 'is-bottom-right',
                type: type,
                pauseOnHover: true,
                closable: false,
                hasIcon: true,
            })
        }
    }
}


export { Notifications };