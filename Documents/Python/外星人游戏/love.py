import pygame


class Love():
    def __init__(self, scree):
        super().__init__()
        self.scree = scree
        self.image = pygame.image.load('外星人游戏/img/love.bmp')
        self.rect = self.image.get_rect()
        self.scree_rect = scree.get_rect()

        self.rect.centerx = self.scree_rect.centerx
        self.rect.centery = self.scree_rect.centery

    def biltLove(self):
        self.scree.blit(self.image, self.rect)
